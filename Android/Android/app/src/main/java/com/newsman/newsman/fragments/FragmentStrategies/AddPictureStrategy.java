package com.newsman.newsman.fragments.FragmentStrategies;

import android.content.Context;

import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.fragments.PicturesFragment;

//TODO ovo je tightly coupled strategy, alternativa je switch i parametar u konsturktoru fragmenta
//pretpostavljam da je bolje da se vrati siwtch, jer je previse tight
public interface AddPictureStrategy {
    void onAddPictureClick(PicturesFragment fragment, Picture picture);
}
