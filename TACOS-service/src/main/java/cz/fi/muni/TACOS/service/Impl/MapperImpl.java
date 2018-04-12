package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.service.annotations.TacosMapper;
import org.dozer.DozerBeanMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ApplicationScoped
@TacosMapper
public class MapperImpl extends DozerBeanMapper {

    public MapperImpl() {
        super();

        List<String> mappingFiles = new ArrayList<>();
        mappingFiles.add("dozerJdk8Converters.xml");
        setMappingFiles(mappingFiles);
    }
}
