package com.lil.MadWorld;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<String> taskText = new ArrayList<String>();
    private final List<String> congratulationText = new ArrayList<String>();

    private boolean isTask = true;

    private int taskIndex = 0;

    private int countOfDays = 0;
    private int countOfNights = 0;
    private int lastIndexOfFirstImage = 0;
//    private boolean isNext = true;

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
        countOfNights = 0;
        countOfDays = 0;

        //показать табличку молодец
       // taskIndex++;
    }

    public boolean tryNextTask(){
        if (!isTask) {
            isTask = true;
            taskIndex++;
            return true;
        }
        return false;
    }

    public String getTaskText(){
        if (isTask) {
            return taskText.get(taskIndex);
        }
        else {
            return congratulationText.get(taskIndex);
        }
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


            switch (taskIndex) {
                case 0:
                    if ((countOfDays >= 2) && (countOfNights >= 2)) {
                        congratulation();
                        return true;
                    }
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

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

}
