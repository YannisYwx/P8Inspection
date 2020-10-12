package com.p8.inspection.data.bean;

import java.util.List;

public class Machines {

    private Page page;
    private List<Machine> machineList;
    public void setPage(Page page) {
        this.page = page;
    }
    public Page getPage() {
        return page;
    }

    public void setMachineList(List<Machine> machineList) {
        this.machineList = machineList;
    }
    public List<Machine> getMachineList() {
        return machineList;
    }
}
