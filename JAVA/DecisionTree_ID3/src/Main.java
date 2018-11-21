import Vector.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import static java.lang.Math.log10;

/**
 *
 * @author DING Rui
 *
 * Realising decision tree algorithm by using the ID3 algorithm
 * The basic method comes from several papers and modules online
 *
 *
 */
public class Main {

    /**
     * Split a line in data text into several independent word
     *
     * @param text the text line that needs to be split
     * @param c the separator, in this case is \t
     * @return a vector of several words in this line
     */
    public static Vector split(String text, char c){
        int index = 0;
        Vector result = new Vector(); //each line would be transformed into a vector of several words
        char[] charText = text.toCharArray();
        for(int i = 0; i < text.length(); i++){
            /* If current letter is \t, then put the last word in to the vector */
            if(charText[i] == c){
                result.addLast(text.substring(index, i));
                index = i + 1;
            }
            if(i == text.length() - 1){
                result.addLast(text.substring(index, i + 1));
            }
        }
        return result;
    }


    /**
     * Transform the raw data file into a vector of single words
     * Put the data and labels together in a hashmap
     *
     * @param fileName name of the original data text file
     * @return
     */
    public static HashMap<Integer, Vector> file2Dataset(String fileName){
        HashMap<Integer, Vector> result = new HashMap<Integer, Vector>();
        if(fileName.isEmpty()){
            return result;
        }
        File file = new File(fileName);
        if(! file.canRead()){
            return result;
        }

        /* Read the data line by line, and transform it into a big vector */
        Vector dataset = new Vector();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while ((tempString = reader.readLine()) != null){
                Vector data = split(tempString, '\t'); //using split method to transform each line in to single words
                dataset.addLast(data);
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException ee){
                    ee.printStackTrace();
                }
            }
        }

        /* Set the labels in advance, in this case the labels are the properties of the patients */
        Vector labels = new Vector();
        Vector age = new Vector();
        age.addLast("age");
        Vector prescription = new Vector();
        prescription.addLast("prescription");
        Vector astigmatic = new Vector();
        astigmatic.addLast("astigmatic");
        Vector tearRate = new Vector();
        tearRate.addLast("tearRate");

        labels.addLast(age);
        labels.addLast(prescription);
        labels.addLast(astigmatic);
        labels.addLast(tearRate);

        /* Put the data and labels in a hashmap, in order to make tree creation more convenient */
        result.put(1, dataset);
        result.put(2, labels);
        return result;
    }


    /**
     * Calculate the Shannon entropy of a specific data property
     * The last property is always the result property
     *
     * @param dataset the last property is the current property
     * @return
     */
    public static double calculationOfShannonEnt(Vector dataset){
        HashMap<String, Integer> labels = new HashMap<>();
        /* Because of the property of java hashmap, initialization and value setting need to be separated */
        for(int i = 0; i < dataset.size(); i++){
            //setting the frequencies of each values of current data property
            String tempData = (String) ((Vector)dataset.get(i)).get(((Vector) dataset.get(i)).size() - 1);
            labels.put(tempData, 0);
        }
        for(int i = 0; i < dataset.size(); i++){
            String tempData = (String) ((Vector)dataset.get(i)).get(((Vector) dataset.get(i)).size() - 1);
            labels.put(tempData, labels.get(tempData) + 1);
        }

        /* Calculation of the Shannon entropy of current property */
        double shannonEnt = 0.0;
        for(String key : labels.keySet()){
            double pi = (double)labels.get(key) / (double)dataset.size();
            shannonEnt += (-pi * log10(pi) / log10(2));
        }

        return shannonEnt;
    }


    /**
     * Cut off a specific value of a specific data property, and get the rest of data
     *
     * e.g. in the case I use
     * before:
     * young	myope	no	reduced	no lenses
     * young	myope	no	normal	soft
     * young	myope	yes	reduced	no lenses
     * young	myope	yes	normal	hard
     * young	hyper	no	reduced	no lenses
     * pre	myope	no	reduced	no lenses
     * pre	myope	no	normal	soft
     * pre	myope	yes	reduced	no lenses
     *
     * after splitDataset(dataset, 0, young):
     * myope    no  reduced no lenses
     * myope    no  normal  soft
     * myope    yes reduced no lenses
     * myope    yes normal  hard
     * hyper    no  reduced no lenses
     *
     * @param dataset the dataset needs to be dealt with
     * @param axis
     * @param value
     * @return
     */
    public static Vector splitDataset(Vector dataset, int axis, String value){
        Vector result = new Vector();
        for(int i = 0; i < dataset.size(); i++){
            Vector data = (Vector)dataset.get(i);
            if (!(data.get(axis)).equals(value)){
                continue;
            }
            Vector subData = new Vector();
            for(int j = 0; j < data.size(); ++j){
                if(j == axis){
                    continue;
                }
                else{
                    subData.addLast(data.get(j));
                }
            }
            result.addLast(subData);
        }

        return result;
    }


    /**
     * Cut off a whole property from the labels, which means that property no longer needs to be discussed
     *
     * @param label
     * @param axis
     * @return
     */
    public static Vector splitLabel(Vector label, int axis){
        Vector result = new Vector();
        for(int i = 0; i < label.size(); i++){
            if(i == axis){
                continue;
            }
            else{
                result.addLast(label.get(i));
            }
        }
        return result;
    }


    /**
     * Make a hashmap of the frequency of each value in a specific data property
     *
     * @param dataset
     * @param axis
     * @return
     */
    public static HashMap<String, Integer> getFeatureClassify(Vector dataset, int axis){
        HashMap<String, Integer> result = new HashMap<>();
        /* hashmap initialization and value setting */
        for(int i = 0; i < dataset.size(); i++){
            Vector tempData = (Vector)dataset.get(i);
            String tempString = (String)tempData.get(axis);
            result.put(tempString, 0);
        }
        for(int i = 0; i < dataset.size(); i++){
            Vector tempData = (Vector)dataset.get(i);
            String tempString = (String)tempData.get(axis);
            int tempI = result.get(tempString);
            result.put(tempString, tempI + 1);
        }
        return result;
    }


    /**
     * Calculate the information gian of a specific data property
     *
     * @param dataset
     * @param axis
     * @return
     */
    public static double getInfoGain(Vector dataset, int axis){
        //get the base Shannon entropy
        double baseShannonEnt = calculationOfShannonEnt(dataset);
        double targetShannonSum = 0.0;

        //calculation is based on the frequency of this data property
        HashMap<String, Integer> classify = getFeatureClassify(dataset, axis);

        /* calculate the Shannon sum of the current property */
        for(String key : classify.keySet()){
            Vector splitedDataset = splitDataset(dataset, axis, key);
            double pi = (double)splitedDataset.size() / (double)dataset.size();
            double shannonEnt = calculationOfShannonEnt(splitedDataset);
            targetShannonSum += pi * shannonEnt;
        }

        //calculate the information gain of current property
        return baseShannonEnt - targetShannonSum;
    }


    /**
     * Find out the property of the best information gain, and cut this property off
     *
     * @param dataset
     * @return
     */
    public static int getBestSplitFeature(Vector dataset){
        double bestInfoGain = 0.0;
        int bestFeatureAxis = 0;
        int featureCount = ((Vector)dataset.get(0)).size() - 1;

        for(int i = 0; i < featureCount; i++){
            double infoGain = getInfoGain(dataset, i); //calculate the information gain of each property remains
            if(infoGain == 0){
                bestInfoGain = infoGain;
                bestFeatureAxis = i;
            }
            else if(infoGain > bestInfoGain){
                bestInfoGain = infoGain;
                bestFeatureAxis = i;
            }
        }

        return bestFeatureAxis;
    }


    /**
     * Called when the last property is considered
     * Give the largest probability that what result each value may get
     * Only be used when there are only one property and result property left
     *
     * @param dataset
     * @param axis
     * @param value
     * @return
     */
    public static String getLargestProbability(Vector dataset, int axis, String value){
        HashMap<String, Integer> result = new HashMap<>();
        /* hashmap initialization and value setting */
        for(int i = 0; i < dataset.size(); i++){
            Vector data = (Vector)dataset.get(i);
            if(!(data.get(axis)).equals(value)){
                continue;
            }
            result.put((String) data.get(1), 0);
        }
        /* set the frequency of the result property */
        for(int i = 0; i < dataset.size(); i++){
            Vector data = (Vector)dataset.get(i);
            if(!(data.get(axis)).equals(value)){
                continue;
            }
            int temp = result.get(data.get(1));
            result.put((String) data.get(1), temp + 1);
        }

        /* find which result this value has most
         * then make it the result of this value */
        int max = 0;
        String v = null;
        for(String temp : result.keySet()){
            if(result.get(temp) > max){
                max = result.get(temp);
                v = temp;
            }
        }
        return v;
    }


    /**
     * Create the decision tree based on the given dataset and the labels
     *
     * @param dataset
     * @param labels
     * @return
     */
    public static Tree createTree(Vector dataset, Vector labels){
        Tree root = new Tree();

        /* if there are only 2 properties left (one of which is the result property) */
        if(((Vector)dataset.get(0)).size() == 2){
            /* the last label becomes the property of the last subtree automatically */
            Vector tempVector = (Vector)labels.get(0);
            String tempString = (String)tempVector.get(0);
            root.setValue(tempString);

            /* using the getLargestProbability method
            * predict the result of each value*/
            HashMap<String, Integer> featureClassify = getFeatureClassify(dataset, 0);
            for(String key : featureClassify.keySet()){
                String result = getLargestProbability(dataset, 0, key);
                Tree node = new Tree(result);
                root.pushNext(node);
                root.pushProperty(key);
            }
            return root;
        }

        /* find out the property with best information gain
        * and set it as the root value of this subtree */
        int axis = getBestSplitFeature(dataset);
        Vector tempVector = (Vector)labels.get(axis);
        String tempString = (String)tempVector.get(0);
        root.setValue(tempString);

        //classify the frequency of the values of current best property
        HashMap<String, Integer> featureClassify = getFeatureClassify(dataset, axis);
        /* create subtrees under each value of this property */
        for(String key : featureClassify.keySet()){
            Vector subDataset = splitDataset(dataset, axis, key);
            Tree node;

            /* if the Shannon entropy of this value of this property is exactly 0
             * then immediately make the result of this value as the value of a new subtree */
            if(calculationOfShannonEnt(subDataset) == 0){
                Vector tempVector1 = (Vector)subDataset.get(0);
                String result = (String)tempVector1.get(tempVector1.size() - 1);
                node = new Tree(result);
            }
            /* if not, split this label, and create the subtree recursively */
            else{
                Vector subLabel = splitLabel(labels, axis);
                node = createTree(subDataset, subLabel);
            }
            root.pushNext(node); //
            root.pushProperty(key); //
        }

        return root;
    }


    /**
     * Based on the tree created by the ID3 and training set, predict the result of the given data
     *
     * @param root
     * @param labels
     * @param data
     * @return
     */
    public static String predict(Tree root, Vector labels, Vector data){
        /* recursively check properties of given data and properties in the tree
        * if still no result when the tree reaches the bottom, then return "unknown" */
        while (root.getNext().size() > 0){
            boolean judge = true;
            for(int i = 0; i < labels.size(); i++){
                //first need to check the labels, which is the reason they also need to be a part of the parameters
                String tempString = (String)labels.get(i);
                /* if current label equals the property of the current root, then go next */
                if(tempString.equals(root.getValue())){
                    /* if the value under current label equals one of the values under current root, then go next */
                    for(int j = 0; j < root.getProperty().size(); j++){
                        String tempString1 = (String)data.get(i);
                        if(tempString1.equals(root.getProperty().get(j))){
                            //make the tree under this value of current property the new root, and recurse again
                            root = (Tree)root.getNext().get(j);
                            judge = false;
                            break;
                        }
                    }
                    break;
                }
            }
            /* if the decision cannot be made, or some of the properties of given data are not in the training set, return unknown */
            if(judge){
                return "Unknown.";
            }
        }
        return root.getValue();
    }


    public static void main(String[] args) {
        HashMap<Integer, Vector> result = file2Dataset("lenses.txt");
        Vector dataset = result.get(1);
        Vector labels = result.get(2);
        Tree tree = createTree(dataset, labels);

        /* should be "soft" */
        Vector data1 = new Vector();
        data1.addLast("pre");
        data1.addLast("myope");
        data1.addLast("no");
        data1.addLast("normal");

        /* should be "no lenses" */
        Vector data2 = new Vector();
        data2.addLast("pre");
        data2.addLast("myope");
        data2.addLast("no");
        data2.addLast("reduced");

        /* should be "hard" */
        Vector data3 = new Vector();
        data3.addLast("presbyopic");
        data3.addLast("myope");
        data3.addLast("yes");
        data3.addLast("normal");

        /* should be "Unknown." */
        Vector data4 = new Vector();
        data4.addLast("1");
        data4.addLast("2");
        data4.addLast("3");
        data4.addLast("4");

        /* labels need to be added manually */
        Vector label = new Vector();
        label.addLast("age");
        label.addLast("prescription");
        label.addLast("astigmatic");
        label.addLast("tearRate");

        System.out.println(predict(tree, label, data1));
        System.out.println(predict(tree, label, data2));
        System.out.println(predict(tree, label, data3));
        System.out.println(predict(tree, label, data4));
    }
}
