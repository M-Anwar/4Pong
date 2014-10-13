/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Audio;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Jason Xu
 */
public class JavaAudio implements Audio{
    private HashMap<String, Clip> music;   
    public static boolean mute = false;    
    
    public JavaAudio(){
        music = new HashMap<>();        
    }
    
    @Override
    public void load(String path, String key) throws Exception{        
        if(music.containsKey(key)) return;
        Clip clip;
                             
        AudioInputStream sound = AudioSystem.getAudioInputStream(
                new BufferedInputStream(
                        ClassLoader.class.getResourceAsStream(path)
                )
        );
        clip = AudioSystem.getClip();
        clip.open(sound);
        music.put(key, clip);        
    }
    
    @Override
    public void play(String key){
        play(key, 0);
    }
    
    @Override
    public void play(String key, int position){
        if(mute) return;
        Clip clip = music.get(key);
        if(clip == null) return;
        if(clip.isRunning()) clip.stop();
        clip.setFramePosition(position);
        while(!clip.isRunning()) clip.start(); 
    }
    
    @Override
    public void stop(String key){
        if(!music.containsKey(key))return;
        Clip clip = music.get(key);
        if(clip.isRunning())clip.stop();
    }
    
    @Override
    public void resume(String key){
        if(!music.containsKey(key))return;
        if(mute) return;
        Clip clip = music.get(key);
        if(!clip.isRunning())clip.start();
    }
    
    @Override
    public void loop(String key){
        loop(key, 0, 0, music.get(key).getFrameLength() - 1);
    }
    
    @Override
    public void loop(String key, int frames){
        loop(key, frames, 0, music.get(key).getFrameLength() - 1);
    }
    
    @Override 
    public void loop(String key, int start, int end){
        loop(key, 0, start, end);
    }
    
    @Override 
    public void loop(String key, int frames, int start, int end){
        if(!music.containsKey(key))return;
        if(mute)return;
        Clip clip = music.get(key);
        clip.setLoopPoints(start, end);
        clip.setFramePosition(frames);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    @Override
    public boolean isPlaying(String key){
        if(!music.containsKey(key))return false;
        if(mute) return false;
        Clip clip = music.get(key);
        if(clip.isRunning())return true;
        else return false;
    }
    
    @Override
    public void setPosition(String key, int frame){
        if(!music.containsKey(key))return;
        Clip clip = music.get(key);
        if(clip.isRunning())clip.stop();
        while(clip.isRunning()) clip.setFramePosition(frame);
    }
    
    @Override
    public long getFrames(String key){
        if(!music.containsKey(key))return -1;
        Clip clip = music.get(key);
        return clip.getLongFramePosition();        
    }
    
    @Override
    public int getPosition(String key){
        if(!music.containsKey(key))return -1;
        Clip clip = music.get(key);
        return clip.getFrameLength();  
    }
    
    @Override 
    public void close(String key){
        this.stop(key);
        music.get(key).close();
    }




}
