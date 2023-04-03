package com.johann.designPattern.dahao.command;

/** 猪肉面命令
 * @ClassName: PorkNoodleCommand
 * @Description:
 * @Author: Johann
 * @Version: 1.0
 **/
public class PorkNoodleCommand implements OrderCommand {

    private Chef chef;

    public PorkNoodleCommand(Chef chef) {
        this.chef = chef;
    }

    @Override
    public void execute() {
        chef.makePorkNoodle();
    }

    @Override
    public void undo() {
        chef.cancelMeal(this);
    }

    @Override
    public void redo() {
        chef.redoPorkNoodle();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
