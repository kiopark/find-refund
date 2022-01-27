package szs.findrefund.api;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

  public <T> SingleResult<T> getSingleResult(T data) {
    SingleResult<T> result = new SingleResult<>();
    result.saveData(data);
    setSuccessResult(result);
    return result;
  }

  public <T> ListResult<T> getSingleResult(List<T> list) {
    ListResult<T> result = new ListResult<>();
    result.saveDataList(list);
    setSuccessResult(result);
    return result;
  }

  public CommonResult getSuccessResult() {
    CommonResult result = new CommonResult();
    setSuccessResult(result);
    return result;
  }

  public CommonResult getFailResult() {
    CommonResult result = new CommonResult();
    setFailResult(result);
    return result;
  }

  private void setSuccessResult(CommonResult result) {
    result.matchCommonResult(true,
        ResponseCode.SUCCESS.getStatus(),
        ResponseCode.SUCCESS.getMessage());
  }

  private void setFailResult(CommonResult result) {
    result.matchCommonResult(false,
        ResponseCode.FAIL.getStatus(),
        ResponseCode.FAIL.getMessage());
  }
}
