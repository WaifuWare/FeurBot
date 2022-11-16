package xyz.someboringnerd.feurbot.modules.StaticBot;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.json.JSONObject;
import xyz.someboringnerd.feurbot.Util.ChatUtil;
import xyz.someboringnerd.feurbot.Util.GlobalVariables;
import xyz.someboringnerd.feurbot.modules.BOT_TYPE;
import xyz.someboringnerd.feurbot.modules.Module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/***************************************************************

 @TODO  : Change the tpbot's code so the base to username match is managed by the api instead of the client.

 @TODO : Maybe some refactoring

 @


 ***************************************************************/
public class TPABot extends Module
{
    public TPABot(String _name, BOT_TYPE _type)
    {
        super(_name, _type);
    }

    private void getWhitelist(String playerName) throws IOException
    {
        Thread t = new Thread(new ThreadedRequest(playerName), "request");
        t.start();
    }


    @Override
    public void onChatMessage(String message)
    {
        if(message.startsWith("Type /tpy"))
        {
            String[] array = message.split(" ");

            try {
                getWhitelist(array[2]);
            } catch (IOException e) {
                ChatUtil.ClientSideMessage(Text.literal(e.getMessage()));
                throw new RuntimeException(e);
            }
        }

    }

}

class ThreadedRequest implements Runnable
{
    private String _Player;
    private static final String USER_AGENT = "Mozilla/5.0";
    public ThreadedRequest(String player_name)
    {
        _Player = player_name;
    }

    public static String getTpaBot(String BaseName)
    {
        switch (BaseName){
            case "achille":
                return "SomeBoringNerd";
            case "degand":
                return "EzN1GGER";
            default:
                return "${b0T} (there is no tpbot at this base)";
        }
    }


    private void autoTpa() throws IOException
    {
        URL obj = new URL("https:// " + GlobalVariables.FeurBotApiUrl + "/member?member=" + _Player.toLowerCase());

        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = httpURLConnection.getResponseCode();
        ChatUtil.ClientSideMessage(Text.literal("Response from API : " + responseCode));

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            System.out.println(json.toString());

            int code = json.getInt("returnCode");
            ChatUtil.ClientSideMessage(Text.literal("API returned §4" + code + "§r"));
            switch (code){
                case 200:
                    ChatUtil.ClientSideMessage(Text.literal(json.getString("canAccessAllBases")));
                    ChatUtil.ClientSideMessage(Text.literal(json.getString("assignedBase")));
                    ChatUtil.ClientSideMessage(Text.literal(json.getString("message")));

                    // @TODO : replace that mess of if / else with an API call so we dont have hard coded values.

                    if(MinecraftClient.getInstance().player.getEntityName().equals("SomeBoringNerd"))
                    {
                        if(json.getString("canAccessAllBases").equals("yes") || json.getString("assignedBase").equals("achille"))
                        {
                            MinecraftClient.getInstance().player.sendCommand("tpy " + json.getString("message"));
                        }else
                        {
                            MinecraftClient.getInstance().player.sendCommand("msg " + json.getString("message") + " you are not assigned to this base, but the base " + json.getString("assignedBase") + ". TPA to " + getTpaBot(json.getString("assignedBase")) + " in order to teleport to your base");
                            MinecraftClient.getInstance().player.sendCommand("tpn " + _Player);
                        }
                    }else if(MinecraftClient.getInstance().player.getEntityName().equals("EzN1GGER"))
                    {
                        if(json.getString("canAccessAllBases").equals("yes") || json.getString("assignedBase").equals("degand"))
                        {
                            MinecraftClient.getInstance().player.sendCommand("tpy " + json.getString("message"));
                        }else
                        {
                            MinecraftClient.getInstance().player.sendCommand("msg " + json.getString("message") + " you are not assigned to this base, but the base " + json.getString("assignedBase") + ". TPA to " + getTpaBot(json.getString("assignedBase")) + " in order to teleport to your base");
                            MinecraftClient.getInstance().player.sendCommand("tpn " + _Player);
                        }
                    }
                    else
                    {
                        MinecraftClient.getInstance().player.sendCommand("msg " + _Player + " you are not a member of FeurGroup or whitelisted to one of our base, but you can join us to fix that mistake here : discord.gg/ReK658v4bs");
                        MinecraftClient.getInstance().player.sendCommand("tpn " + _Player);
                    }
                    break;
                case 404:
                    MinecraftClient.getInstance().player.sendCommand("msg " + _Player + " you are not a member of FeurGroup or whitelisted to one of our base, but you can join us to fix that mistake here : discord.gg/ReK658v4bs");
                    MinecraftClient.getInstance().player.sendCommand("tpn " + _Player);
                    break;
                default:
                    MinecraftClient.getInstance().player.sendCommand("msg " + _Player + " against all ods, you somehow managed to trigger an impossible event to occur. Are you proud ? (you should be)");
                    MinecraftClient.getInstance().player.sendCommand("tpn " + _Player);
                    break;
            }
        }else{
            MinecraftClient.getInstance().player.sendCommand("msg " + _Player + " an unknown error has occured and I can't communicate with the API at the moment. here's the error code : " + responseCode);
            MinecraftClient.getInstance().player.sendCommand("tpn " + _Player);
        }
    }

    @Override
    public void run()
    {
        try {
            autoTpa();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}