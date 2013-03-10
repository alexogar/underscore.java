package org.underscore.util;

/**
 * User: alexogar
 * Date: 3/7/13
 * Time: 1:58 AM
 */
public enum VariationType {
    COLLECTION {
        @Override
        public String present(String genericType) {
            return "Collection<"+genericType+">";
        }
    },ARRAY {
        @Override
        public String present(String genericType) {
            return genericType+"[]";
        }
    };

    public abstract String present(String genericType);
}
