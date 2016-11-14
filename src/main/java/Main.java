import bean.SerialPortConfigBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Lilx
 * @since 2016
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        SerialPortConfigBean config = (SerialPortConfigBean) context.getBean("testInSpring");
        System.out.println(config.getPort() + config.getBaudrate());
        System.out.println("end");
    }
}
