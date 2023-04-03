package com.johann.designPattern.dahao.command;

/** 牛肉面命令
 * @ClassName: BeefNoodleCommand
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class BeefNoodleCommand implements OrderCommand {

    private Chef chef;

    public BeefNoodleCommand(Chef chef) {
        this.chef = chef;
    }

    @Override
    public void execute() {
        chef.makeBeefNoodle();
    }

    @Override
    public void undo() {
        chef.cancelMeal(this);
    }

    @Override
    public void redo() {
        chef.redoBeefNoodle();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}