import Helper.ExtractData;
import Helper.User;
import Operation.LoginController;
import Operation.UserSignupController;
import Operation.TaskOperation;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Listener.class)
public class TestClass {
    Logger log = extentController.log;
    ExtentReports extent = extentController.extent;
    @Test (priority = 1)
    void registerUser() {
        ExtentTest regUserTest = extent.createTest("Register User Test");
        UserSignupController op = new UserSignupController(regUserTest, extentController.log, extentController.baseUrl);
        for (User user: ExtractData.getAllUsers()) {
            log.debug(user);
            op.registerUser(user);
        }
    }
    @Test (priority = 2)
    void login() {
        ExtentTest loginTest = extent.createTest("User session");
        TaskOperation taskOperation = new TaskOperation(extentController.baseUrl, loginTest, extentController.log);
        taskOperation.login(ExtractData.getSingleRandomUser());
        taskOperation.init();
        taskOperation.addTasks();
        taskOperation.getTask(5);
    }

    @Test (priority = 3)
    void registerSingleUser_negativeTest() {
        ExtentTest negRegisterTest = extent.createTest("Registering with already registered user");
        negRegisterTest.info("Testing neg Test");
        UserSignupController negReg = new UserSignupController(negRegisterTest, extentController.log, extentController.baseUrl);
        assert negReg.registerUser(ExtractData.getFirstOrLast(true));
    }

    @Test (priority = 4)
    void neg_login_Test() {
        ExtentTest negLoginTest = extent.createTest("Negative Login test");
        LoginController loginController = new LoginController(extentController.baseUrl, negLoginTest, extentController.log);
        negLoginTest.info("Testing for neg Login");
        loginController.login(ExtractData.getFirstOrLast(false));

    }

    @Test (priority = 5)
    void negAddTask() {
        ExtentTest negAddTaskTest = extent.createTest("Negative Add Task test");
        TaskOperation operation = new TaskOperation(extentController.baseUrl, negAddTaskTest, extentController.log);
        operation.login(ExtractData.getFirstOrLast(true));
        JSONObject badObjectType = new JSONObject();
        badObjectType.put("desc", "add_task");
        operation.init();
        operation.addTask(badObjectType);
    }

    @Test (priority = 6)
    void deleteAllUsers() {
        ExtentTest delTest = extent.createTest("Delete All user");
        LoginController session = new LoginController(extentController.baseUrl, delTest, extentController.log);
        for (User user: ExtractData.getAllUsers()) {
            session.login(user);
            session.deleteUser(user);
        }
    }
}
