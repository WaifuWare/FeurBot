package xyz.someboringnerd.feurbot.modules;

public class Module
{

    public String name;
    public BOT_TYPE type;

    public Module(String _name, BOT_TYPE _type)
    {
    }

    // That function get triggered when a chat message is received.
    public void onChatMessage(String message){}
}
