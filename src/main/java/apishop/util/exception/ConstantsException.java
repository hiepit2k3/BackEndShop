package apishop.util.exception;

// Lớp này trả về trạng thái hoạt động:exception, đường dẫn, link dẫn tới client
public class ConstantsException {

    public static final class Exception {
        public static final class Common {
            public static final String INVALID_PARAM_CODE = "000001";
            public static final String INVALID_PARAM = "Invalid request params";
            public static final String ID_MUST_BE_NULL_CODE = "000002";
            public static final String ID_MUST_BE_NULL = "Id must be null when creating new entity";
            public static final String FOREIGN_KEY_IS_NOT_FOUND_CODE = "000003";
            public static final String FOREIGN_KEY_IS_NOT_FOUND = "Id foreign key is not found";
            public static final String CAN_NOT_CHANGE_CODE = "0003";
            public static final String CAN_NOT_CHANGE = " can not change";
            public static final String CAN_NOT_ADD_CODE = "0004";
            public static final String CAN_NOT_ADD = " can not add";
            public static final String CAN_NOT_DELETE_CODE = "0005";
            public static final String CAN_NOT_DELETE = " because it is used in another table";
        }
    }

    public static final class Entity {
        public static final String ENTITY_NOT_FOUND_CODE = "000100";
        public static final String ENTITY_NOT_FOUND = "Entity not found";
        public static final String ENTITY_ALREADY_EXIST_CODE = "000101";
        public static final String ENTITY_ALREADY_EXIST = "Entity already exist";

    }



}
