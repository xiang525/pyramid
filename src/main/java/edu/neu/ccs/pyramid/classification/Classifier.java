package edu.neu.ccs.pyramid.classification;

import edu.neu.ccs.pyramid.dataset.ClfDataSet;
import edu.neu.ccs.pyramid.dataset.FeatureRow;

import java.io.*;
import java.util.stream.IntStream;

/**
 * Created by chengli on 8/13/14.
 */
public interface Classifier extends Serializable{
    int predict(FeatureRow featureRow);

    default int[] predict(ClfDataSet dataSet){
        return IntStream.range(0, dataSet.getNumDataPoints()).parallel().
                map(i -> predict(dataSet.getFeatureRow(i))).toArray();
    }

    default void serialize(File file) throws Exception{
        File parent = file.getParentFile();
        if (!parent.exists()){
            parent.mkdirs();
        }
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        ){
            objectOutputStream.writeObject(this);
        }
    }

    default void serialize(String file) throws Exception{
        serialize(new File(file));
    }

}
