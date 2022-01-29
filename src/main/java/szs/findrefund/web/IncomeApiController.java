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
import szs.findrefund.service.income.IncomeService;
import szs.findrefund.util.JWTUtil;
import szs.findrefund.web.dto.refund.RefundResponseDto;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "Scrap / Refund")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/szs")
public class IncomeApiController {

  private final IncomeService incomeService;

  @ApiOperation(value = "URL 스크랩", notes = "제공된 스크랩 URL 을 호출 합니다.")
  @PostMapping("/scrap")
  public ResponseEntity<String> userScrap(HttpServletRequest request) throws Exception {
    String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    incomeService.saveScrapData(jwtToken);
    return ResponseEntity.ok("API 요청 완료!");
  }

  @ApiOperation(value = "환급 조회", notes = "환급 정보를 조회 합니다.")
  @GetMapping("/refund")
  public ResponseEntity<RefundResponseDto> refund(HttpServletRequest request) throws Exception {
    String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    Long idFromToken = JWTUtil.getIdFromToken(jwtToken);
    return ResponseEntity.ok().body(incomeService.selectMyRefund(idFromToken));
  }

}