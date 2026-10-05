package com.example;

import java.util.Arrays;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

public class Setup implements OptionsFactory {
    @Override
    public Options getOptions() {
        return new Options().setLaunchOptions(new BrowserType.LaunchOptions()
            .setHeadless(false)
            .setArgs(Arrays.asList("--disable-gpu")))
            .setTestIdAttribute("data-test");
            
    }
}