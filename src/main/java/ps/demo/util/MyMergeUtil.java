package ps.demo.util;


import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.apache.commons.collections4.CollectionUtils;
import ps.demo.common.MyBaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyMergeUtil {

    public static <T extends MyBaseEntity> List<List<T>> splitToNewDelUpdate(List<T> latest_entities, List<T> obsolete_entities) {
        List<List<T>> newDelUpdate = new ArrayList<>();
        if (CollectionUtils.isEmpty(latest_entities)) {
            newDelUpdate.add(null);
            newDelUpdate.add(obsolete_entities);
            newDelUpdate.add(null);
            return newDelUpdate;
        }

        if (CollectionUtils.isEmpty(obsolete_entities)) {
            newDelUpdate.add(latest_entities);
            newDelUpdate.add(null);
            newDelUpdate.add(null);
            return newDelUpdate;
        }

        //add param - db
        List<T> newAdded = substractById(latest_entities, obsolete_entities);
        newDelUpdate.add(newAdded);

        //del db - param
        List<T> deleted = substractById(obsolete_entities, latest_entities);
        newDelUpdate.add(deleted);

        //update db & param
        List<T> toUpdate = substractById(latest_entities, newAdded);
        newDelUpdate.add(toUpdate);

        return newDelUpdate;
    }


    public static <T extends MyBaseEntity> List<T> substractById(List<T> as, List<T> bs) {
        List<Long> bids = bs.stream().map(b -> b.getId()).collect(Collectors.toList());
        return as.stream().filter(a -> !bids.contains(a.getId())).collect(Collectors.toList());
    }


}

