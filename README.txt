Language detector that will guess the origin language of a word using a neural network with the encog framework.
Languages supported: French, English.




To run the word evaluation program, use the following command:
    ./gradlew runEval
From here, you can have the neural network guess the language of an input word.

To open the GUI for Encog, use the following command:
    gui/encogGui
Or the long way, from the gui directory:
    java -jar encog-workbench-3.3.0-executable.jar
In the gui, navigate to the directory src/main/resources. The trained neural network is vectors_train.egb.

****To add a training set, continue training etc, manipulate the neural network
get word database from some source, manipulate it using the useful utilities
in the folder DictionaryAnalysisCode.

Make sure you format it properly: one word per line.

Use the WordVector class to convert your database into arrays, WordVector will
save it as a .csv. You can then open it with the workbench gui, run the wizard
create networks, manipulate other networks.
