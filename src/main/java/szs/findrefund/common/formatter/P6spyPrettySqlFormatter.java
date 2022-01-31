package szs.findrefund.common.formatter;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class P6spyPrettySqlFormatter implements MessageFormattingStrategy {
  @Override
  public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
    sql = formatSql(category, sql);
    Date currentDate = new Date();

    SimpleDateFormat formatDate = new SimpleDateFormat("yy.MM.dd HH:mm:ss");

    return formatDate.format(currentDate) + " | "+ "OperationTime : "+ elapsed + "ms" + sql;
  }

  private String formatSql(String category,String sql) {
    if(sql ==null || sql.trim().equals("")) return sql;

    if (Category.STATEMENT.getName().equals(category)) {
      String tmpSql = sql.trim().toLowerCase(Locale.ROOT);

      if(tmpSql.startsWith("create") || tmpSql.startsWith("alter") || tmpSql.startsWith("comment")) {
        sql = FormatStyle.DDL.getFormatter().format(sql);
      }else {
        sql = FormatStyle.BASIC.getFormatter().format(sql);
      }
    }

    return sql;
  }
}
