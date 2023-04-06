package com.johann.designPattern.designPatterns23.P_command;

/** 鸡肉面命令
 * @ClassName: ChickenNoodleCommand
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class ChickenNoodleCommand implements OrderCommand {

    private Chef chef;

    public ChickenNoodleCommand(Chef chef) {
        this.chef = chef;
    }


    @Override
    public void execute() {
        chef.makeChickenNoodle();
    }

    @Override
    public void undo() {
        chef.cancelMeal(this);
    }

    @Override
    public void redo() {
        chef.redoChickenNoodle();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
