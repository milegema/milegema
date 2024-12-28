package com.bitwormhole.passwordgm.data.repositories.objects;

import com.bitwormhole.passwordgm.data.ids.ObjectID;

public interface ObjectManager {

    ObjectHolder get(ObjectID id);

    ObjectWriter writer();

}
