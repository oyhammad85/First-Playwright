package com.example;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Unit test for simple App.
 */
@UsePlaywright(Setup.class)
public class AppTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    @BeforeEach
    void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setArgs(Arrays.asList("--disable-gpu")));
        browserContext = browser.newContext();
        page = browserContext.newPage();
        page.navigate("https://practicesoftwaretesting.com");
        playwright.selectors().setTestIdAttribute("data-test");

    }
    @AfterEach
    void tearDown() {
        browserContext.close();
        browser.close();
        playwright.close();
    }

    @Test
    void getPageTitle(Page page) {

        String title = page.title();
        System.out.println(title);
        page.locator("#search-query").fill("Playwright");

    }

    @Test
    void clickPliers(Page page) {

        List<String> productList = page.getByTestId("product-name")
                .filter(new Locator.FilterOptions().setHasText(" Bolt Cutters ")).allTextContents();
        PlaywrightAssertions.assertThat(page.getByTestId("product-name")).hasCount(productList.size());
        // Assertions.assertThat(productList.get(0)).contains("Bolt Cutters");

        System.out.println(productList.size());
        // page.getByTestId("search-query").fill("Playwright");
        // var pliers = page.getByRole(AriaRole.LINK, new
        // Page.GetByRoleOptions().setName("Bolt Cutters"));
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(page.locator("#search-query").inputValue()).isEqualTo("test");
        softly.assertAll();
    }

    @Test
    public void uploadFile(Page page) {
        page.getByTestId("nav-contact").click();

        FileChooser chooser = page.waitForFileChooser(() -> page.getByText("Upload").click());
        chooser.setFiles(Paths.get("src/test/resources/files/sample.pdf"));
        page.locator("input[type=file]").setInputFiles(
                new FilePayload("note.txt", "text/plain", "hello".getBytes(StandardCharsets.UTF_8)));
        PlaywrightAssertions.assertThat(page.getByText("sample.pdf")).isVisible();

    }

    @Test
    void waitHandleing() {
        // page.waitForSelector("[data-test=product-name]"); // Wait for the element to
        // be present in the DOM
        page.waitForSelector("[data-test=product-name]", //type 1
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000)); // Wait for
                                                                                                            // the
                                                                                                            // element
                                                                                                            // to be
                                                                                                            // visible type 2
        //page.waitForCondition(() -> page.getByTestId("product-name").isVisible()); Type 3
        // page.waitForResponse("https://practicesoftwaretesting.com/wp-content/uploads/2023/07/bolt-cutters.jpg", () -> {
        //     page.getByTestId("product-name").click();
        // });   Type 4
        List<String> productList = page.getByTestId("product-name").allInnerTexts();
        Assertions.assertThat(productList).contains("Bolt Cutters");

    }

}