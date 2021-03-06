package com.example.algamoney.api.storage;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.algamoney.api.config.property.AlgamoneyApiProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
public class S3 {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AlgamoneyApiProperty property;

    private static final Logger logger = LoggerFactory.getLogger(S3.class);

    public String salvarTemporariamente(MultipartFile arquivo) {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(arquivo.getContentType());
        objectMetadata.setContentLength(arquivo.getSize());

        try {
            String nomeUnico = gerarNomeUnico(arquivo.getOriginalFilename());
            PutObjectRequest putObjectRequest = new PutObjectRequest(property.getS3().getBucket(),
                                                                     nomeUnico, arquivo.getInputStream(),
                                                                     objectMetadata)
                                                .withAccessControlList(acl);

            putObjectRequest.setTagging(new ObjectTagging(Collections.singletonList(new Tag("expirar", "true"))));

            amazonS3.putObject(putObjectRequest);

            if(logger.isDebugEnabled()){
                logger.debug("Arquivo {} enviado com sucesso para o S3.", arquivo.getOriginalFilename());
            }
            return nomeUnico;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Problemas ao tentar enviar o arquivo");
        }

    }

    public String configurarUrl(String objeto){
        return "https://" + property.getS3().getBucket() + ".s3-"+ Regions.SA_EAST_1.getName() +".amazonaws.com/"+objeto;
    }

    public void salvar(String object) {
        SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest(property.getS3().getBucket(),
                                                                                      object,new ObjectTagging(Collections.emptyList()));
        amazonS3.setObjectTagging(setObjectTaggingRequest);
    }

    public void remover(String objeto) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(property.getS3().getBucket(), objeto);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    private String gerarNomeUnico(String originalFilename) {
        return UUID.randomUUID().toString() +"_"+ originalFilename;
    }

    public void substituir(String objetoAntigo, String objetoNovo) {
        if(StringUtils.hasText(objetoAntigo)){
            this.remover(objetoAntigo);
        }
        salvar(objetoNovo);
    }
}
