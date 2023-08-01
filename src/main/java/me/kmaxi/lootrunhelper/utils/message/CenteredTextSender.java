package me.kmaxi.lootrunhelper.utils.message;

import me.kmaxi.lootrunhelper.utils.CodingUtils;

public class CenteredTextSender {
    private final static int CENTER_PX = 154;

    public static void sendCenteredMessage(String message){
        if(message == null || message.equals(""))
            return;
        CodingUtils.msg(getCompensatedMessage(message, CENTER_PX));
    }

    public static void sendLeftCenteredMessage(String message){
        if(message == null || message.equals(""))
            return;
        CodingUtils.msg(getCompensatedMessage(message, CENTER_PX / 2));
    }

    public static void sendCenteredMessage(String leftMessage, String rightMessage) {
        if (leftMessage == null || leftMessage.isEmpty() || rightMessage == null || rightMessage.isEmpty()) {
            return;
        }

        String compensatedLeftMessage = getCompensatedMessage(leftMessage, CENTER_PX / 2);

        int leftSize = getTotalMessageSize(compensatedLeftMessage);



        int messagePxSize = getTotalMessageSize(rightMessage);


        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = (CENTER_PX / 2) + CENTER_PX - halvedMessageSize - leftSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        String rightMessageCompensated = (sb + rightMessage);
        CodingUtils.msg(compensatedLeftMessage + rightMessageCompensated);

    }

        private static String getCompensatedMessage(String message, int center_px) {

        int messagePxSize = getTotalMessageSize(message);


        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = center_px - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }
        return (sb + message);

    }

    private static int getTotalMessageSize(String message) {
        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
                continue;
            }

            if(previousCode){
                previousCode = false;
                if(c == 'l' || c == 'L'){
                    isBold = true;

                }else isBold = false;

                continue;
            }
            DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
            messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
            messagePxSize++;
        }
        return messagePxSize;
    }



}
