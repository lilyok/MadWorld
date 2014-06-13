package com.lil.MadWorld;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static final int WEREWOLF = 0;
    private static final int HUNTER = 1;
    private static final int FAY = 2;
    private static final int WIZARD = 3;


    private final List<String> taskText = new ArrayList<String>();
    private final List<String> congratulationText = new ArrayList<String>();

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
    public TaskManager(){
        taskText.add("Уровень0 'Новобранец'. Вас обратили. Вас преследует злой ястреб, вы не можете причинить ему вред, " +
                "а он может убить Вас только, когда вы в полете. Все прочие существа - смертны." +
                " Продержитесь 1 луны и 2 солнца. Кровь = жизнь, " +
                " феячая кровь = защита от солнца (+ жизнь, если не из горлА, а из гОрла)");
        taskText.add("Уровень1 'Пожиратель'. 2 луны и 2 солнца не используйте склянок с кровью, использовав сбросите счетчик времени");
        taskText.add("Уровень2 'Собиратель'. Соберите 3 танцующие ромашки, 2 склянки с феячей кровью и 1 с настоящей. Не убивайте волшебников."); //увеличить максимальное здоровье

        taskText.add("Уровень3 'Дипломат'. Облетайте наземных врагов 2 луны и 2 солнца. Вступив в бой, вы сбросите счетчик"); // приз - скорость перемещения выше в образе летучей мыши

        taskText.add("Уровень4 'Фейский угодник'. 2 луны и 2 солнца убивайте всех существ кроме фей. Жалость = сброс счетчика.");     // приз - кольцо-защита от солнца на 1 сутки на каждом уровне - потом подзарядка до следующего уровня
        //--------------------------------------------------------------------------------------------------------------

        congratulationText.add("Поздравляю! Отныне никто не посмеет назвать Вас сосунком =)");

        congratulationText.add("Вегетарианство и пацифизм - не Ваш конек. Вы проявили себя, как настоящий потрошитель");

        congratulationText.add("Вы оставили местных бездомных без стеклотары - придется им идти на разбой. " +
                "Зато волшебники в благодарность за лояльность сделали в склянках из цветов отвар, " +
                "замедляющий потерю крови. Теперь Вы выносливее");

        congratulationText.add("Какая маневренность! Отныне на крыльях вы перемещаетесь в 2 раза быстрее");


        congratulationText.add("Феи рады, что никто из их сестер не умер за последнюю пару суток. " +
                "А еще больше, что умерла куча других существ. Они преподносят вам кольцо, замедляющее процесс сгорания.");


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
                fields.add("Уровень");
                fields.add("танцующих ромашек");
                fields.add("склянок с феячей кровью");
                fields.add("склянок с настоящей кровью");

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

                break;
            case 5:

                break;
            case 6:

                break;
        }
//            return false;
    }

    private void fillSunMoonCounter() {
        fields.clear();
        fields.add("Уровень");
        fields.add("солнц");
        fields.add("лун");

        values.clear();
        values.add(String.valueOf(taskIndex));
        values.add(String.valueOf(countOfDays));
        values.add(String.valueOf(countOfNights));

    }


    public String getTaskText(){
        if (isTask) {
            return taskText.get(taskIndex);
        }
        else {
            return congratulationText.get(taskIndex);
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
}
