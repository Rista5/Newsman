package com.newsman.newsman.fragments.AddToAdapter;

import com.newsman.newsman.ServerEntities.Picture;
import com.newsman.newsman.adapters.PicturesListAdapter;

public interface ModifyStrategy {
    void addPicture(PicturesListAdapter adapter, Picture picture);
    void removePicture(PicturesListAdapter adapter, Picture picture);
}
