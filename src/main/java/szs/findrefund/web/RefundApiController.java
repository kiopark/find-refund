package szs.findrefund.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import szs.findrefund.service.refund.RefundService;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "Scrap / Refund")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/szs")
public class RefundApiController {

  private final RefundService refundService;

  @ApiOperation(value = "URL 스크랩", notes = "제공된 스크랩 URL 을 호출 합니다.")
  @PostMapping("/scrap")
  public ResponseEntity<String> userScrap(HttpServletRequest request) throws Exception {
    String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    refundService.saveScrapData(jwtToken);
    return ResponseEntity.ok("성공!");
  }

  @ApiOperation(value = "환급 조회", notes = "환급 받을 세액공제를 조회 합니다.")
  @GetMapping("/refund")
  public ResponseEntity<String> refund(HttpServletRequest request) throws Exception {
    String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    refundService.selectMyRefund(jwtToken);
    return ResponseEntity.ok("성공!");
  }

}