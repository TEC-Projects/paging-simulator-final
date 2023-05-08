package com.pagingsimulator.pagingsimulator.Model;

import java.util.Collection;
import java.util.LinkedList;

public class PagingAlgorithmSimulationStatus {
    private Collection<Page> pages;
    private int simulatedProcesses;
    private int simulationElapsedTime;
    private int thrashingTime;
    private int ramUsage;
    private int vRamUsage;
    private int internalFragmentationVolume;
    private int loadedPages;
    private int unloadedPages;

    public PagingAlgorithmSimulationStatus(Collection<Page> pages) {
        this.pages = pages;
        this.simulatedProcesses = 0;
        this.simulationElapsedTime = 0;
        this.thrashingTime = 0;
        this.ramUsage = 0;
        this.vRamUsage = 0;
        this.internalFragmentationVolume = 0;
    }

    public PagingAlgorithmSimulationStatus() {
        this.pages = null;
        this.simulatedProcesses = 0;
        this.simulationElapsedTime = 0;
        this.thrashingTime = 0;
        this.ramUsage = 0;
        this.vRamUsage = 0;
        this.internalFragmentationVolume = 0;
    }

    public Collection<Page> getPages() {
        return pages;
    }

    public void setPages(Collection<Page> pages) {
        this.pages = pages;
    }

    public int getSimulationElapsedTime() {
        return simulationElapsedTime;
    }

    public void setSimulationElapsedTime(int simulationElapsedTime) {
        this.simulationElapsedTime = simulationElapsedTime;
    }

    public int getThrashingTime() {
        return thrashingTime;
    }

    public void setThrashingTime(int thrashingTime) {
        this.thrashingTime = thrashingTime;
    }

    public int getRamUsage() {
        return ramUsage;
    }

    public void setRamUsage(int ramUsage) {
        this.ramUsage = ramUsage;
    }

    public int getVRamUsage() {
        return vRamUsage;
    }

    public void setVRamUsage(int vRamUsage) {
        this.vRamUsage = vRamUsage;
    }

    public int getInternalFragmentationVolume() {
        return internalFragmentationVolume;
    }

    public void setInternalFragmentationVolume(int internalFragmentationVolume) {
        this.internalFragmentationVolume = internalFragmentationVolume;
    }

    public int getSimulatedProcesses() {
        return simulatedProcesses;
    }

    public void setSimulatedProcesses(int simulatedProcesses) {
        this.simulatedProcesses = simulatedProcesses;
    }

    public int getvRamUsage() {
        return vRamUsage;
    }

    public void setvRamUsage(int vRamUsage) {
        this.vRamUsage = vRamUsage;
    }

    public int getLoadedPages() {
        return loadedPages;
    }

    public void setLoadedPages(int loadedPages) {
        this.loadedPages = loadedPages;
    }

    public int getUnloadedPages() {
        return unloadedPages;
    }

    public void setUnloadedPages(int unloadedPages) {
        this.unloadedPages = unloadedPages;
    }

    @Override
    public String toString() {
        return "PagingAlgorithmSimulationStatus{\n" +
                "pages=" + pages +
                ",\n simulationElapsedTime=" + simulationElapsedTime +
                ",\n thrashingTime=" + thrashingTime +
                ",\n ramUsage=" + ramUsage +
                ",\n vRamUsage=" + vRamUsage +
                ",\n internalFragmentationVolume=" + internalFragmentationVolume +
                "}\n\n";
    }
}
