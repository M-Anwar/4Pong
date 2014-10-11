/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.BufferedInputStream;
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 *
 * @author Jason Xu
 */
public class JavaBackgroundMusic implements BackgroundMusic{
    private static HashMap<String, Clip> Music;
    private static int gap;
    private static boolean mute = false;
    
    @Override
    public void init(){
        Music = new HashMap<String, Clip>();
        gap = 0;
    }
    
    @Override
    public void load(String path, String key) throws Exception{
        if(Music.containsKey(key)) return;
        Clip clip;
        try{
            File file = new File(path);
            if(file.exists()){
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(sound);
                Music.put(key, clip);
            }
            else{
                throw new RuntimeException("Sound: file not found " + path);
            }
        }finally{
            
        }
    }
    
    @Override
    public void play(String key){
        play(key, 0);
    }
    
    @Override
    public void play(String key, int position){
        if(mute) return;
        Clip clip = Music.get(key);
        if(clip == null) return;
        if(clip.isRunning()) clip.stop();
        clip.setFramePosition(position);
        while(!clip.isRunning()) clip.start(); 
    }
    
    @Override
    public void stop(String key){
        if(!Music.containsKey(key))return;
        Clip clip = Music.get(key);
        if(clip.isRunning())clip.stop();
    }
    
    @Override
    public void resume(String key){
        if(!Music.containsKey(key))return;
        if(mute) return;
        Clip clip = Music.get(key);
        if(!clip.isRunning())clip.start();
    }
    
    @Override
    public void loop(String key){
        loop(key, 0, 0, Music.get(key).getFrameLength() - 1);
    }
    
    @Override
    public void loop(String key, int frames){
        loop(key, frames, 0, Music.get(key).getFrameLength() - 1);
    }
    
    @Override 
    public void loop(String key, int start, int end){
        loop(key, gap, start, end);

    }
    
    @Override 
    public void loop(String key, int frames, int start, int end){
        if(!Music.containsKey(key))return;
        if(mute)return;
        Clip clip = Music.get(key);
        clip.setLoopPoints(start, end);
        clip.setFramePosition(frames);
    }
    
    @Override
    public boolean isPlaying(String key){
        if(!Music.containsKey(key))return false;
        if(mute) return false;
        Clip clip = Music.get(key);
        if(clip.isRunning())return true;
        else return false;
    }
    
    @Override
    public void setPosition(String key, int frame){
        if(!Music.containsKey(key))return;
        Clip clip = Music.get(key);
        if(clip.isRunning())clip.stop();
        while(clip.isRunning()) clip.setFramePosition(frame);
    }
    
    @Override
    public long getFrames(String key){
        if(!Music.containsKey(key))return -1;
        Clip clip = Music.get(key);
        return clip.getLongFramePosition();        
    }
    
    @Override
    public int getPosition(String key){
        if(!Music.containsKey(key))return -1;
        Clip clip = Music.get(key);
        return clip.getFrameLength();  
    }
    
    @Override 
    public void close(String key){
        
    }




}
