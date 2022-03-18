package ps.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ps.demo.dto.Person;
import ps.demo.dto.response.PersonResponse;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;

@Tag(name = "This is a file controller")
@Slf4j
@Controller
@RequestMapping(value = "/api/file")
public class FileController {


    @Operation(summary = "Say hi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Say hi success or fail",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonResponse.class))}),
    })
    @RequestMapping(value = "/hi", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public PersonResponse hi(
            @RequestParam(value = "name", defaultValue = "Spring", required = false) String name) {
        log.info("--->>File hi~, name={}", name);
        if ("Spring".equals(name)) {
            throw new BadRequestException(CodeEnum.BAD_REQUEST, false, "Name Spring is not valid!");
        }
        Person person = new Person();
        person.setId(System.currentTimeMillis());
        person.setNickName(name);
        PersonResponse response = new PersonResponse();
        response.setData(person);
        return response;
    }

    @Operation(summary = "Say hi data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Say hi data success or fail",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonResponse.class))}),
    })
    @RequestMapping(value = "/hi-data", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public PersonResponse hiWithData(
            @RequestParam(value = "name", defaultValue = "Spring", required = false) String name) {
        log.info("--->>File hi~, name={}", name);
        Person person = new Person();
        person.setNickName(name);
        return new PersonResponse(person);
    }



}
