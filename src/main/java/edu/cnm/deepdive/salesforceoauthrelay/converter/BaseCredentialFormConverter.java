package edu.cnm.deepdive.salesforceoauthrelay.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cnm.deepdive.salesforceoauthrelay.model.BaseCredential;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class BaseCredentialFormConverter extends AbstractHttpMessageConverter<BaseCredential> {

  private static final FormHttpMessageConverter converter = new FormHttpMessageConverter();
  private static final ObjectMapper mapper = new ObjectMapper();

  @Override
  protected boolean supports(Class<?> aClass) {
    return (BaseCredential.class == aClass);
  }

  @Override
  protected BaseCredential readInternal(Class<? extends BaseCredential> aClass,
      HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
    Map<String, String> map = converter.read(null, httpInputMessage).toSingleValueMap();
    return mapper.convertValue(map, BaseCredential.class);
  }

  @Override
  protected void writeInternal(BaseCredential baseCredential, HttpOutputMessage httpOutputMessage)
      throws IOException, HttpMessageNotWritableException {
      Map<String, Object> map =
          mapper.convertValue(baseCredential, new TypeReference<Map<String, Object>>() {});
      MultiValueMap<String, Object> mvmap = new LinkedMultiValueMap<>();
      mvmap.setAll(map);
      converter.write(mvmap, MediaType.APPLICATION_FORM_URLENCODED, httpOutputMessage);
  }

}
