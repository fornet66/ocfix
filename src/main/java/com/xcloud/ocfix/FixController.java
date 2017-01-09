package com.xcloud.ocfix;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FixController {

    @Autowired
    private FixUtils fixUtils;
    @Autowired
    private UserUtils userUtils;

    @RequestMapping(value = "/getusers.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserVO> getUsers() {
        List<UserVO> list = userUtils.findUsers();
        return list;
    }

    @RequestMapping(value = "/getfiles.do/{storage}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FileCacheVO> getFiles(@PathVariable Integer storage) {
        List<FileCacheVO> list = fixUtils.findFiles(storage);
        return list;
    }

    @RequestMapping(value = "/fixfiles.do", method = RequestMethod.POST)
    public void fixFiles() {
        fixUtils.fixit(null);
    }

}
