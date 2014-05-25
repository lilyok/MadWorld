package com.lil.MadWorld;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<String> taskText = new ArrayList<String>();
    private final List<String> congratulationText = new ArrayList<String>();
    private boolean isTask = true;
    private int taskIndex = 0;
    private boolean isNext = true;

    public TaskManager(){
        taskText.add("Уровень 'Выживший'. Продержитесь 2 луны и 2 солнца. Помните, кровь = жизнь, " +
                "а феячая кровь = защита от солнца (+ жизнь, если не из горлА, а из гОрла)");
        taskText.add("Уровень 'Собиратель стеклотары'. Соберите 6 склянок с феячей кровью и 8 с настоящей.");
        taskText.add("Уровень 'Пожиратель'. 2 луны и 2 солнца не используйте склянок с кровью.");
        taskText.add("Уровень 'Летчик'. Будьте незаметны для существ в образе летучей мыши 2 луны и 2 солнца. " +
                "Наградой будет навык"); // приз - скорость перемещения выше в образе летучей мыши
        taskText.add("Уровень 'Собиратель'. Соберите букет из пяти ромашек. Вам будет кому его преподнести"); // надо в следующем задании, ромашек пока в мир не ввела
        taskText.add("Уровень 'Гомеопат'. Не убивайте волшебников и в конце они сплетут вам венок из ромашек"); // венок замедляет потерю крови

        taskText.add("Уровень 'Феёвый дипломат'. Завоюйте доверие фей. 2 луны и 2 солнца не кусайте феечек и " +
                "вас ждет подарок");     // приз - кольцо-защита от солнца. на последних уровнях
//        taskText.add("");


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

        //показать табличку молодец
       // taskIndex++;
    }

    public void setNextTask(){
        isTask = true;
        taskIndex++;
    }

    public String getTaskText(){
        if (isTask) {
            return taskText.get(taskIndex);
        }
        else {
            return congratulationText.get(taskIndex);
        }
    }

    public boolean calculate(int index) {
        return false;
    }

}
