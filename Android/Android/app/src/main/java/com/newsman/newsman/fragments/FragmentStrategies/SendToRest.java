package com.newsman.newsman.fragments.FragmentStrategies;

import com.newsman.newsman.REST.Put.PutPictureToRest;
import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.fragments.PicturesFragment;

public class SendToRest implements AddPictureStrategy {
    @Override
    public void onAddPictureClick(PicturesFragment fragment, Picture picture) {
        new PutPictureToRest(picture).put();
    }
}
