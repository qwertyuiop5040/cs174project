mv *.class oldbin
javac -Xdiags:verbose -cp '.:ojdbc6.jar' *.java
java -cp '.:ojdbc6.jar' UserGUI
# java -cp '.:ojdbc6.jar' TellerGUI