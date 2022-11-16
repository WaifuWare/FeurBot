package xyz.someboringnerd.feurbot.Util;

public class GlobalVariables
{
    // regex for the message where you get asked to type /tpy or /tpaccept with the username of the
    // person that want to tpa.
    public static final String TPARequestRegex = "Type /tpy ";

    // note : by default it is <[a-zA-Z0-9_]{2,16}> for the username part
    public static final String ChatFormatRegex = "<[a-zA-Z0-9_]{2,16}>";

    // url / ip / whatever to the API.
    // https://github.com/SomeBoringNerd/FeurAPI
    // for more info about it.
    // please note that such an API is mandatory.
    public static final String FeurBotApiUrl = "feurgroup-api.falling-from.space";

    // please never do any commit with the password in it.
    public static final String FeurBotApiPassword = "password";
}