package com.retailstreet.mobilepos.Model;

    public class SyncResponse {
        String Message;
        String OutputValuesKeys;

        public SyncResponse(String message, String outputValuesKeys) {
            Message = message;
            OutputValuesKeys = outputValuesKeys;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        public String getOutputValuesKeys() {
            return OutputValuesKeys;
        }

        public void setOutputValuesKeys(String outputValuesKeys) {
            OutputValuesKeys = outputValuesKeys;
        }

        @Override
        public String toString() {
            return "SyncResponse{" +
                    "Message='" + Message + '\'' +
                    ", OutputValuesKeys='" + OutputValuesKeys + '\'' +
                    '}';
        }
    }

