/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Audio;

/**
 * An interface that is used to implement the music
 * @author Jason Xu
 */

public interface Audio {
    
    
    /**
    * Draws the specified string on the screen
    * @param path the path of the sound file
    * @param key the key that is associated with the sound
    */
    public void load(String path, String key) throws Exception;
    
    /**
    * Play the sound file associated with the key
    * @param key the key that is associated with the sound
    */ 
    public void play(String key);
    
    /**
    * Play the sound file associated with the key
    * @param key the key that is associated with the sound
    * @param position the frame position of the sound to start from
    */
    public void play(String key, int position);
    
    /**
    * Stop playing the sound file associated with the key
    * @param key the key that is associated with the sound
    */
    public void stop(String key);
    
    /**
    * Resume playing the sound file associated with the key
    * @param key the key that is associated with the sound
    */
    public void resume(String key); 
    
    /**
    * Loop playing the sound file associated with the key
    * @param key the key that is associated with the sound
    */
    public void loop(String key);
    
    /**
    * Check if the sound file is currently playing
    * @param key the key that is associated with the sound
    */
    public boolean isPlaying(String key);

    /**
    * Loop playing the sound file associated with the key and starting on the frame
    * @param key the key that is associated with the sound
    * @param frames the frame to start the sound file on
    */
    public void loop(String key, int frames);

    /**
    * Loop playing the sound file associated with the key
    * @param key the key that is associated with the sound
    * @param start set the start Loop point
    * @param end set the end Loop point
    */
    public void loop(String key, int start, int end);

    /**
    * Loop playing the sound file associated with the key
    * @param key the key that is associated with the sound
    * @param start set the start Loop point
    * @param end set the end Loop point
    * @param frames the frame to start the sound file on
    */
    public void loop(String key, int frames, int start, int end);

    /**
    * start playing the sound file associated with the key and starting on the frame
    * @param key the key that is associated with the sound
    * @param frames the frame to start the sound file on
    */
    public void setPosition(String key, int frames);

    /**
    * get which frame the sound file is playing associated with the key
    * @param key the key that is associated with the sound
    */
    public long getFrames(String key);
    
    /**
    * get which positions the sound file is playing associated with the key
    * @param key the key that is associated with the sound
    */
    public int getPosition(String key);
    
    /**
    * close the sound file if it is playing associated with the key
    * @param key the key that is associated with the sound
    */
    public void close(String key);  
}
