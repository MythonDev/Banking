package banking_system;

import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.experimental.categories.Categories;

@Categories.SuiteClasses({ServerControllerTest.class, DataBaseControllerTest.class, AccountDataTest.class})
@RunWith(Categories.class)
@Categories.IncludeCategory(TestEntity.class)
@Categories.ExcludeCategory(TestControl.class)
public class TestRunnerExclude {
}
