package ar.edu.iua.treban.utils;

public class RoleUtils {
    public enum RoleName {
        DEVELOPER("DEV"),
        PROJECT_LEADER("PROJECT_LEAD");

        private String value;

        public String getValue() {
            return value;
        }

        RoleName(String value) {
            this.value = value;
        }
    }
}