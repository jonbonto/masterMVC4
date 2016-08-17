package com.jatismobile.controller;

import ch.qos.logback.core.CoreConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.SearchResults;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TweetController {

    @Autowired
    private Twitter twitter;

    @RequestMapping("/")
    public String home() {
        return "searchPage";
    }

    @RequestMapping("/result")
    public String hello(@RequestParam(defaultValue = "masterSpringMVC4") String search, Model model) {
        SearchResults searchResults = twitter.searchOperations().
                search(search);
        List<Tweet> tweets = searchResults.getTweets();
        model.addAttribute("tweets", tweets);
        model.addAttribute("search", search);
        return "resultPage";
    }

    @RequestMapping(value = "/postSearch", method = RequestMethod.POST)
    public String postSearch(HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String search = request.getParameter("search");
        System.out.println(search+" "+search.toLowerCase().contains("struts"));
        if (search.toLowerCase().contains("struts")) {
            redirectAttributes.addFlashAttribute("error", "Try using spring instead!");
            return "redirect:/";
        }
        redirectAttributes.addAttribute("search", search);
        return "redirect:result";
    }
}
