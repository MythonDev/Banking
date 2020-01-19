package banking_system;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ServerController {

    private DataBaseController controller = DataBaseController.getInstance();

    @RequestMapping("/banking")
    public String mainPage(){
        return "banking";
    }

    @RequestMapping("/new_user")
    public String newUser(@RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName, @RequestParam("password") String password,
                          @RequestParam("date") String dateOfBirth, @RequestParam("personal_ID") long personalNumber){
        controller.createUser(personalNumber, firstName, lastName, dateOfBirth, password);
        return "user_created";
    }

    @RequestMapping("/login")
    public String login(HttpSession session , Model model, @RequestParam("login_number") Long login, @RequestParam("password") String password){
        String loginResult = controller.checkLogin(login, password);
        if(loginResult.equals("Invalid"))
            return "invalid_login";
        if(loginResult.equals("User")){
            CustomerData customer = controller.getUserData(login);
            session.setAttribute("customer", customer);
            model.addAttribute("customer_description", customer.toString());
            List<AccountData> list = controller.getAccounts(login);
            session.setAttribute("customer_accounts", list);
            return "login_user";
        }
        else if(loginResult.equals("Worker")){
            WorkerData worker = controller.getWorkerData(login);
            session.setAttribute("worker", worker);
            model.addAttribute("worker", worker.toString());
            return "worker_login";
        }
        return null;
    }

    @RequestMapping("/account")
    public String account(HttpSession session, Model model, @RequestParam("number") long account_number){
        model.addAttribute("account_number", account_number);
        session.setAttribute("account_number", account_number);
        return "account";
    }

    @RequestMapping("/transfer")
    public String transfer(HttpSession session, Model model, @RequestParam("destination") long destination, @RequestParam("value") double amount){
        if(!controller.findAccount(destination))
            model.addAttribute("message", "Nie znaleziono konta docelowego");
        boolean success = controller.makeTransfer((long) session.getAttribute("account_number"), destination, amount);
        List<AccountData> list = (List<AccountData>) session.getAttribute("customer_accounts");
        for(AccountData a : list){
            if(a.getAccountNumber() == (long) session.getAttribute("account_number"))
                if(!a.changeBalance(-amount)) {
                    model.addAttribute("message", "Niewystarczające środki na koncie!");
                    break;
                }
        }
        if(success)
            return "transfer_success";

        return "transfer_failure";
    }

    @RequestMapping("/main_interface")
    public String backToDashboard(Model model, HttpSession session){
        model.addAttribute("customer_description", controller.getUserData(((CustomerData)session.getAttribute("customer")).getPersonalID()).toString());
        session.setAttribute("customer_accounts", controller.getAccounts( ((CustomerData) session.getAttribute("customer")).getPersonalID()));
        return "login_user";
    }

    @RequestMapping("/main_interface_worker")
    public String backToWorkerDashboard(HttpSession session, Model model){
        model.addAttribute("worker", ((WorkerData)session.getAttribute("worker")).toString());
        return "worker_login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "banking";
    }

    @RequestMapping("/operation")
    public String makeOperation(@RequestParam("account_number") long accountNumber, @RequestParam("amount") double value){
       boolean success = controller.changeBalance(accountNumber, value);
       if(success)
           return "operation_success";
       else
           return "operation_failure";
    }

    @RequestMapping("/client_accounts")
    public String listAccounts(@RequestParam("surname") String surname, Model model){
        model.addAttribute("list", controller.getAccountsListString(surname));
        return "account_list";
    }

    @RequestMapping("/new_account")
    public String createAccount(@RequestParam("customer_number") long personalNumber, @RequestParam("currency") String currency, @RequestParam("product_name") String productName){
        if(controller.createAccount(personalNumber, currency, productName))
            return "account_created";
        return "account_not_created";
    }
}
