package lessons.messenger.constant;

public class WebServiceConstants
{
    public static final String ROOT = "http://train.sandbox.eutech-ssii.com/messenger/";

    public static class LOGIN
    {
        public static final String URI = ROOT + "login.php";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TOKEN = "token";
    }

    public static class CONTACTS
    {
        public static final String URI = ROOT + "contacts.php";
        public static final String TOKEN = "token";
        public static final String CONTACTS = "contacts";

        public static final String ID = "id";
        public static final String CONTACT = "contact";
        public static final String MESSAGE = "message";

        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String EMAIL = "email";

        public static final String DATE = "date";
    }

    public static class MESSAGES
    {
        public static final String URI = ROOT + "messages.php";
        public static final String TOKEN = "token";
        public static final String CONTACT = "contact";
        public static final String MESSAGES = "messages";

        public static final String MESSAGE = "message";
        public static final String DATE = "date";
        public static final String SENT = "sent";
    }

    public static class MESSAGE
    {
        public static final String URI = ROOT + "message.php";
        public static final String TOKEN = "token";
        public static final String CONTACT = "contact";
        public static final String MESSAGE = "message";

        public static final String DATE = "date";
        public static final String SENT = "sent";
    }
}