package b303.farm.user.controller;
import b303.farm.user.dto.UserInfoDTO;
import b303.farm.user.service.MyPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @Autowired
    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/{email}")
    public UserInfoDTO getUserInfoByEmail(@PathVariable String email) {
        return myPageService.getUserInfoByEmail(email);
    }
}
