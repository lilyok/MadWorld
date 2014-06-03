package com.lil.MadWorld;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<String> taskText = new ArrayList<String>();
    private final List<String> congratulationText = new ArrayList<String>();

    private boolean isTask = true;

    private int taskIndex = 2;

    private int countOfDays = 0;
    private int countOfNights = 0;

    private int countOfFayBlood = 0;
    private int countOfTrueBlood = 0;

    private boolean isFlying = false;


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

    public void setFlying(boolean isFlying) {
        this.isFlying = isFlying;
    }

    public TaskManager(){
        taskText.add("Уровень 'Выживший'. Продержитесь 2 луны и 2 солнца. Помните, кровь = жизнь, " +
                "а феячая кровь = защита от солнца (+ жизнь, если не из горлА, а из гОрла)");
        taskText.add("Уровень 'Собиратель стеклотары'. Соберите 3 склянки с феячей кровью и 2 с настоящей.");
        taskText.add("Уровень 'Пожиратель'. 2 луны и 2 солнца не используйте склянок с кровью, использовав сбросите счетчик времени");
        taskText.add("Уровень 'Летчик'. Будьте незаметны для существ в образе летучей мыши 2 луны и 2 солнца. " +
                "Наградой будет навык"); // приз - скорость перемещения выше в образе летучей мыши
        taskText.add("Уровень 'Собиратель'. Соберите букет из пяти ромашек. Вам будет кому его преподнести"); // надо в следующем задании, ромашек пока в мир не ввела
        taskText.add("Уровень 'Гомеопат'. Не убивайте волшебников и в конце они сплетут вам венок из ромашек"); // венок замедляет потерю крови

        taskText.add("Уровень 'Феёвый дипломат'. Завоюйте доверие фей. 2 луны и 2 солнца не кусайте феечек и " +
                "вас ждет подарок");     // приз - кольцо-защита от солнца. на последних уровнях
        //--------------------------------------------------------------------------------------------------------------

        congratulationText.add("Поздравляю! Отныне никто не посмеет назвать Вас сосунком =)");
        congratulationText.add("Целый уровень у Вас не было необходимости кого-либо убивать. Но вы лишили легального " +
                "заработка немало бродяг, но всплеск преступности - не Ваша забота");
        congratulationText.add("Вегетарианство и пацифизм - не Ваш конек. Вы проявили себя, как настоящий потрошитель");
        congratulationText.add("Кажется, воздух ваша родная стихия! Отныне на крыльях вы перемещаетесь в 2 раза быстрее");
        congratulationText.add("Еще чуть-чуть и феечки и бабочки приняли бы вас за своего. Как хорошо, что букет уже " +
                "собран и можно не портить вампирскую репутацию столь миленьким занятием");
        congratulationText.add("Волшебники начали Вам доверять (быть может, и зря). Они сплели из Ваших ромашек венок, " +
                "способный замедлять потерю крови, а значит и голод");
        congratulationText.add("Феи рады, что никто из их сестер не умер за последнюю пару суток. " +
                "Они преподносят вам кольцо, защищающее от солнца, в надежде, что не имея потребности в феячей крови," +
                " вы не станете ее проливать");


        prepareStatusOfTask();
    }

//    public int getTaskIndex() {
//        return taskIndex;
//    }
//
//    public void setTaskIndex(int taskIndex) {
//        this.taskIndex = taskIndex;
//    }


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
                fields.clear();
                fields.add("склянок с феячей кровью");
                fields.add("склянок с настоящей кровью");
                values.clear();
                values.add(String.valueOf(countOfFayBlood));
                values.add(String.valueOf(countOfTrueBlood));

                break;
            case 2:
                fillSunMoonCounter();
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
        fields.add("солнц");
        fields.add("лун");

        values.clear();
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
//    public ArrayList<ArrayList<String>> getStatusOfTask(){
//        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
//
//        result.add(fields);
//        result.add(values);
//        return result;
//    }
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
                    if ((countOfFayBlood >= 3) && (countOfTrueBlood >= 2)) {
                        congratulation();
                        return true;
                    }

                    break;
                case 2:
                    if ((countOfFayBlood > 0) || (countOfTrueBlood > 0)){
                        clearAll();
                    } else if ((countOfDays >= 2) && (countOfNights >= 2)) {
                        congratulation();
                        return true;
                    }

                    break;
                case 3:
                    if (!isFlying) {
                        clearAll();
                    } else if ((countOfDays >= 2) && (countOfNights >= 2)) {
                        congratulation();
                        return true;
                    }
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
        return false;
    }

    private void clearAll() {
        countOfDays = 0;
        countOfNights = 0;
        countOfFayBlood = 0;
        countOfTrueBlood = 0;
        fields.clear();
        values.clear();
    }

}
