package com.lil.MadWorld;

import android.content.Context;
import android.content.SharedPreferences;
import com.lil.MadWorld.Models.Vampire;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static final int WEREWOLF = 0;
    private static final int HUNTER = 1;
    private static final int FAY = 2;
    private static final int WIZARD = 3;


    private final List<String> taskText = new ArrayList<String>();
    private final List<String> congratulationText = new ArrayList<String>();
    private final Context context;

    private boolean isTask = true;


    private int taskIndex = 0;

    private int countOfDays = 0;
    private int countOfNights = 0;

    private int countOfFayBlood = 0;
    private int countOfTrueBlood = 0;

    private int countOfFlowers = 0;


  //  private boolean isFlying = false;


    private int lastIndexOfFirstImage = 0;
    private ArrayList<String> fields = new ArrayList<String>();
    private ArrayList<String> values = new ArrayList<String>();

//    private boolean isNext = true;

    public void incCountOfTrueBlood() {
        this.countOfTrueBlood++;
    }

    public void incCountOfFayBlood() {
        this.countOfFayBlood++;
    }

   // public void setFlying(boolean isFlying) {
   //     this.isFlying = isFlying;
   // }



    //объединить собирателя стеклотары и букет(рисовать)+ гомеопат  - убивать можно всех кроме волшебников
    //объединить летчик и орлов - нарисовать: облетайте врагов на земле, обходите врагов с воздуха
    //Феечек   - договор с феями уничтожать др существ
    public TaskManager(Context context){
        this.context = context;
        taskText.add(context.getString(R.string.task0));
        taskText.add(context.getString(R.string.task1));
        taskText.add(context.getString(R.string.task2));
        taskText.add(context.getString(R.string.task3)); // приз - скорость перемещения выше в образе летучей мыши
        taskText.add(context.getString(R.string.task4));
        //--------------------------------------------------------------------------------------------------------------

        congratulationText.add(context.getString(R.string.congratulation0));

        congratulationText.add(context.getString(R.string.congratulation1));

        congratulationText.add(context.getString(R.string.congratulation2));

        congratulationText.add(context.getString(R.string.congratulation3));

        congratulationText.add(context.getString(R.string.congratulation4));


        prepareStatusOfTask();
    }



    private void congratulation(){
        isTask = false;
        clearAll();

        //показать табличку молодец
       // taskIndex++;
    }

    public boolean tryNextTask(){
        if (!isTask) {
            prepareStatusOfTask();
            isTask = true;
            taskIndex++;
            return true;
        }
        return false;
    }

    private void prepareStatusOfTask() {
        switch (taskIndex) {
            case 0:
                fillSunMoonCounter();
                break;
            case 1:
                fillSunMoonCounter();
                break;
            case 2:
                fields.clear();
                fields.add(context.getString(R.string.taskString));
                fields.add(context.getString(R.string.dancingFlowerString));
                fields.add(context.getString(R.string.bootleOfFayBloodString));
                fields.add(context.getString(R.string.bootleOfTrueBloodString));

                values.clear();
                values.add(String.valueOf(taskIndex));
                values.add(String.valueOf(countOfFlowers));
                values.add(String.valueOf(countOfFayBlood));
                values.add(String.valueOf(countOfTrueBlood));
                break;
            case 3:
                fillSunMoonCounter();
                break;
            case 4:
                fillSunMoonCounter();
                break;
            default:
                fields.clear();
                fields.add(context.getString(R.string.newTaskString));
                values.clear();
                values.add(context.getString(R.string.noString));
        }
//            return false;
    }

    private void fillSunMoonCounter() {
        fields.clear();
        fields.add(context.getString(R.string.taskString));
        fields.add(context.getString(R.string.sunString));
        fields.add(context.getString(R.string.moonString));

        values.clear();
        values.add(String.valueOf(taskIndex));
        values.add(String.valueOf(countOfDays));
        values.add(String.valueOf(countOfNights));

    }


    public String getTaskText(){
        if (taskIndex < taskText.size()) {
            if (isTask) {
                return taskText.get(taskIndex);
            } else {
                return congratulationText.get(taskIndex);
            }
        } else {
            return context.getString(R.string.noNewTaskString);
        }
    }

    public boolean isHaveNewInformation(){
        if ((fields.size() == 0) || (values.size() == 0))
            return  false;
        return true;
    }
    public ArrayList<String> getFields(){
        return fields;
    }

    public ArrayList<String> getValues(){
        return values;
    }

    public void fighting(int typeEnemy){
        if ((taskIndex == 2) && (typeEnemy == WIZARD))
            clearAll();
        else if ((taskIndex == 4) && (typeEnemy == FAY))
            clearAll();
        else if (taskIndex == 3)
            clearAll();
    }

    public void sparingEnemy(int typeEnemy){
         if ((taskIndex == 4) && (typeEnemy != FAY))
            clearAll();
    }

    public boolean calculate(int indexOfFirstImage) {
        if (isTask) {
            if (indexOfFirstImage != lastIndexOfFirstImage) {
                lastIndexOfFirstImage = indexOfFirstImage;
                if (indexOfFirstImage == 0)
                    countOfDays++;
                else
                    countOfNights++;
            }

            prepareStatusOfTask();
            switch (taskIndex) {
                case 0:
                    if ((countOfDays >= 2) && (countOfNights >= 2)) {
                        congratulation();
                        return true;
                    }
                    break;
                case 1:
                    if ((countOfFayBlood > 0) || (countOfTrueBlood > 0)){
                        clearAll();
                    } else if ((countOfDays >= 2) && (countOfNights >= 2)) {
                        congratulation();
                        return true;
                    }

                    break;
                case 2:
                    if ((countOfFlowers >= 3) && (countOfFayBlood >= 2) && (countOfTrueBlood >= 1)) {
                        congratulation();
                        return true;
                    }

                    break;
                case 3:
                    if ((countOfDays >= 2) && (countOfNights >= 2)) {
                        congratulation();
                        return true;
                    }
                    break;
                case 4:
                    if ((countOfDays >= 2) && (countOfNights >= 2)) {
                        congratulation();
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    private void clearAll() {
        countOfDays = 0;
        countOfNights = 0;
        countOfFayBlood = 0;
        countOfTrueBlood = 0;
        countOfFlowers = 0;

        fields.clear();
        values.clear();
    }



    public void savePreferences(SharedPreferences.Editor ed) {
        ed.putInt("taskIndex", taskIndex);
        ed.putInt("countOfDays", countOfDays);
        ed.putInt("countOfNights", countOfNights);
        ed.putInt("countOfFayBlood", countOfFayBlood);
        ed.putInt("countOfTrueBlood", countOfTrueBlood);
        ed.putInt("lastIndexOfFirstImage", lastIndexOfFirstImage);
        ed.putInt("countOfFlowers", countOfFlowers);

    //    ed.putBoolean("isFlying", isFlying);
        ed.putBoolean("isTask", isTask);
    }

    public void restorePreferences(SharedPreferences sPref) {
        taskIndex = sPref.getInt("taskIndex", 0);
        countOfDays = sPref.getInt("countOfDays", 0);
        countOfNights = sPref.getInt("countOfNights", 0);
        countOfFayBlood = sPref.getInt("countOfFayBlood", 0);
        countOfTrueBlood = sPref.getInt("countOfTrueBlood", 0);
        lastIndexOfFirstImage = sPref.getInt("lastIndexOfFirstImage", 0);
        countOfFlowers = sPref.getInt("countOfFlowers", 0);

    //    isFlying = sPref.getBoolean("isFlying", false);
        isTask = sPref.getBoolean("isTask", true);

    }

    public void addFlower() {
        countOfFlowers++;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public int rewarding(Vampire vampire) {
        switch (taskIndex) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:   //замедление потери крови
                vampire.setWeakenCoefficient(2);
                return 3;
            case 4:   //скорость в образе мыши выше
             //   vampire.setBatSpeedCoefficient(2);
                return 4;
            case 5:   //замедление сгорания
                vampire.setSunCoefficient(2);
                return 5;
        }

        return -1;
    }
}
