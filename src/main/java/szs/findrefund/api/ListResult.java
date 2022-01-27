package szs.findrefund.api;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResult<T> extends CommonResult {
  private List<T> list;

  public void saveDataList(List<T> data) {
    this.list = list;
  }
}
