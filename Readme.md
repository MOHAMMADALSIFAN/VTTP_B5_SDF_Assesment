task 1 compile 

javac --source-path . task01/src/vttp/batch5/sdf/task01/models/*.java task01/src/vttp/batch5/sdf/task01/*.java -d classes

run 

java -cp classes vttp.batch5.sdf.task01.Main "filepath.csv"

task 2 compile 

javac --source-path . task02/src/vttp/batch5/sdf/task02/*.java -d classes   

run

java -cp classes vttp.batch5.sdf.task02.Main "filepath.csv"