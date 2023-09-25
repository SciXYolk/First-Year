import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class ChatBot extends Robot implements Chatty{ 

    // AI level of the chatbot
    private int level;

    // A list of friends of the chatbot
    private final List<ChatBot> friends;

    // A list of chats made by the chatbot
    private final List<String> chatRecords;
    Random R = new Random();
    
    /**
     * Create a chatbot
     *
     * @param name  A given name
     * @param level A a given level
     */
    public ChatBot(String name, int level) {
        super(name);
        if(level < Chatty.LEVEL_MIN){
            this.level = Chatty.LEVEL_MIN;
        }else if(level > Chatty.LEVEL_MAX){
            this.level = Chatty.LEVEL_MAX;
        } else{
            this.level = level;
        }

        friends = new ArrayList<>();
        chatRecords = new ArrayList<>();
    }

    /**
     * Add a chatbot to the friends list
     *
     * @param bot A given chatbot
     */
    public void addFriend(ChatBot bot) {
        
        if(!friends.contains(bot) && bot != null && !bot.equals(this)){
            friends.add(bot);
        }
        
    }

    public boolean hasAI(){
        return level > LEVEL_MIN;
    }
    
    public String question(){
        String[] keys = QA.keySet().toArray(new String[QA.keySet().size()]);
        String randomKey = keys[R.nextInt(keys.length)];
        if(hasAI() == false){
            addChatRecord('Q', "Good");
            return "Good";
        }else{
            addChatRecord('Q', randomKey);
            return randomKey;    
        }
        
        
        
    }
    
    public String answer(String question){
        if(hasAI() == true){
            if(QA.containsKey(question)){
                String answer = QA.get(question);
                addChatRecord('A', answer);
                return answer;
            }else{
                return "Interesting question";
            }
        }else{
            return "Excellent";
        }
    }
    
    public int[] getChatStats(){
        Set<String> uniqueQ = new HashSet<>();
        Set<String> uniqueA = new HashSet<>();
        for(String q: chatRecords){
            if(q.charAt(0) == 'Q'){
                uniqueQ.add(q);
            }
        }
        for(String a: chatRecords){
            if(a.charAt(0) == 'A'){
                uniqueA.add(a);
            }
        }
        int numUniqueQ = uniqueQ.size();
        int numUniqueA = uniqueA.size();
        int[] totalAmount = {numUniqueQ, numUniqueA};
        return totalAmount;
    }
          
    
    

    //////////////////////////////////////////////////////////////////////////////////
    /////////    PLEASE DO NOT CHANGE CODE BELOW THIS LINE    ////////////////////////

    /**
     * Get the chatbot's level
     *
     * @return The level of the Chatbot
     */
    public int getLevel() {
        
        return level;
    }

    /**
     * Get the chatbot's friends
     *
     * @return The list of friends of the Chatbot
     */
    public List<ChatBot> getFriends() {
        
        return friends;
    }

    /**
     * Get the chat records
     *
     * @return The list of chats made by the chatbot
     */
    public List<String> getChatRecords() {
        
        return chatRecords;
    }

    /**
     * Add a chat by the chatbot in chatRecord
     * <p>
     * Each chat record is prefixed
     * by "Q:" for a question
     * or "A:" for an answer
     * e.g. if the chatbot asks a question "How are you?",
     * the string "Q:How are you?" will be added in the chatRecords
     *
     * @param type A given chat type, either 'Q' or 'A'
     * @param chat A given chat
     */
    public void addChatRecord(char type, String chat) {
        
        if (type == 'Q' || type == 'A') {
            chatRecords.add(type + ":" + chat);
        }
        else {
            System.out.println("Wrong type.");
        }
    }

    /**
     * @param obj A given object
     * @return true if obj is a Chatbot with the same name and level,
     * false otherwise
     */
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChatBot bot)) {
            return false;
        }
        return super.equals(bot) && level == bot.getLevel();        
    }
}