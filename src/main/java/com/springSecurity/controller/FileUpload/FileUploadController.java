package com.springSecurity.controller.FileUpload;

import com.github.pagehelper.PageHelper;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;

import com.qcloud.cos.transfer.Download;
import com.springSecurity.config.TencentCosConfig;
import com.springSecurity.config.TencentCosProperties4Picture;
import com.springSecurity.entity.User;
import com.springSecurity.from.DeleteBucketKeyFrom;
import com.springSecurity.from.UploadFrom;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.packing.FdfsClientWrapper;
import com.springSecurity.result.Result;
import com.springSecurity.service.ShopingSliders.ShopingSlidersService;
import com.springSecurity.service.product.ProductImg.ProductImgService;
import com.springSecurity.service.user.UserService;
import com.sun.jmx.snmp.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RestController
public class FileUploadController {
//    @Autowired
//    private UploadService uploadService;

    @Autowired
    private FdfsClientWrapper fdfsClientWrapper;

    @Resource
    private TencentCosProperties4Picture tencentCosProperties4Picture;

    @Resource
    private TencentCosConfig tencentCosConfig;

    @Autowired
    private ProductImgService productImgService;

    @Autowired
    UserService userService;

    @Resource
    @Qualifier(TencentCosConfig.COS_IMAGE)
    private COSClient cosClient4Picture;



    @Autowired
    private ShopingSlidersService shopingSlidersService;



    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IllegalStateException, IOException{
        String oldFileName = file.getOriginalFilename(); // ???????????????????????????
        String saveFilePath = "C://image";
        String newFileName = UUID.randomUUID() + ".png";
        // ?????????
        File newFile = new File(saveFilePath + "\\" + newFileName);
        // ?????????????????????????????????
        file.transferTo(newFile);
        return null;
    }

    @PostMapping("/upload-file-tencent-cos5")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getUploadFile2TencentCosUrl5(@RequestParam("file") MultipartFile multipartFile,Principal principal){

        log.info("getUploadFile2TencentCosUrl fucntion of FileController start !");
        User user=userService.getByUsername(principal.getName());
        File localFile = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            log.info("fileName = {}",originalFilename);
            String[] filename = originalFilename.split("\\.");
            localFile=File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(localFile);
            localFile.deleteOnExit();
        } catch (IOException e) {
            log.info("MultipartFile transto file IOException ={}",e.getMessage());

        }

        if(localFile == null){
            System.out.println(localFile+"??????");
        }

        String key = TencentCosConfig.COS_IMAGE + "/" + new Date().getTime() + ".png";
        log.info("key = {}", key);

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(tencentCosProperties4Picture.getBucketName(), key, localFile);
        //?????????????????? ???????????????
        putObjectRequest.setStorageClass(StorageClass.Standard);

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            //putObjectResult ?????????etag
            String etag = putObjectResult.getETag();
            log.info("eTag = {}", etag);
        } catch (CosServiceException e) {
            log.error("CosServiceException ={}", e.getMessage());
            throw new CosServiceException(e.getMessage());
        } catch (CosClientException e) {
            log.error("CosClientException ={}", e.getMessage());
            throw new CosClientException(e.getMessage());
        }
         cosClient.shutdown();
         String url = tencentCosProperties4Picture.getBaseUrl()+ "/" + key;

         userService.insert(user.getUsername(),url);

            return Result.succ(200,"??????",url);

    }


    @PostMapping("upload-file-tencent-cos")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getUploadFile2TencentCosUrl(@RequestParam("file") MultipartFile multipartFile,Principal principal){

//        log.info("getUploadFile2TencentCosUrl fucntion of FileController start !");
//        User user=userService.getByUsername(principal.getName());
//        File localFile = null;
//        try {
//            String originalFilename = multipartFile.getOriginalFilename();
//            log.info("fileName = {}",originalFilename);
//            String[] filename = originalFilename.split("\\.");
//            localFile=File.createTempFile(filename[0], filename[1]);
//            multipartFile.transferTo(localFile);
//            localFile.deleteOnExit();
//        } catch (IOException e) {
//            log.info("MultipartFile transto file IOException ={}",e.getMessage());
//
//        }
//
//        if(localFile == null){
//            System.out.println(localFile+"??????");
//        }
//
//        String key = TencentCosConfig.COS_IMAGE + "/" + new Date().getTime() + ".png";
//        log.info("key = {}", key);
//
//        PutObjectRequest putObjectRequest =
//                new PutObjectRequest(tencentCosProperties4Picture.getBucketName(), key, localFile);
//        //?????????????????? ???????????????
//        putObjectRequest.setStorageClass(StorageClass.Standard);
//
//        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();
//
//        try {
//            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
//            //putObjectResult ?????????etag
//            String etag = putObjectResult.getETag();
//            log.info("eTag = {}", etag);
//        } catch (CosServiceException e) {
//            log.error("CosServiceException ={}", e.getMessage());
//            throw new CosServiceException(e.getMessage());
//        } catch (CosClientException e) {
//            log.error("CosClientException ={}", e.getMessage());
//            throw new CosClientException(e.getMessage());
//        }
//        cosClient.shutdown();
//        String url = tencentCosProperties4Picture.getBaseUrl()+ "/" + key;
//
//        userService.insert(user.getUsername(),url);
//
//            return Result.succ(200,"??????",url);

         String URL = "https://imgbucket-1307396532.cos.ap-guangzhou.myqcloud.com/";

        String prefix="image/";

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        String bucketName = "imgbucket-1307396532";

        String fileName = multipartFile.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            multipartFile.transferTo(localFile);
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            // ??? ??????????????? COS
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,localFile);
            cosClient.putObject(objectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }

        return Result.succ(200,"??????",URL+fileName);

    }


    @PostMapping("upload-file-tencent-cos1")
    @LimitRequest(count=30)
    public Result getUploadFile2TencentCosUrl1(@RequestParam("file") MultipartFile multipartFile,Principal principal){

        log.info("getUploadFile2TencentCosUrl fucntion of FileController start !");
        User user=userService.getByUsername(principal.getName());
        File localFile = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            log.info("fileName = {}",originalFilename);
            String[] filename = originalFilename.split("\\.");
            localFile=File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(localFile);
            localFile.deleteOnExit();
        } catch (IOException e) {
            log.info("MultipartFile transto file IOException ={}",e.getMessage());

        }

        if(localFile == null){
            System.out.println(localFile+"??????");
        }

        String key = TencentCosConfig.COS_IMAGE + "/" + new Date().getTime() + ".png";
        log.info("key = {}", key);

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(tencentCosProperties4Picture.getBucketName(), key, localFile);
        //?????????????????? ???????????????
        putObjectRequest.setStorageClass(StorageClass.Standard);

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            //putObjectResult ?????????etag
            String etag = putObjectResult.getETag();
            log.info("eTag = {}", etag);
        } catch (CosServiceException e) {
            log.error("CosServiceException ={}", e.getMessage());
            throw new CosServiceException(e.getMessage());
        } catch (CosClientException e) {
            log.error("CosClientException ={}", e.getMessage());
            throw new CosClientException(e.getMessage());
        }
        cosClient.shutdown();
        String url = tencentCosProperties4Picture.getBaseUrl()+ "/" + key;

//        productImgService.insert(url);

        return Result.succ(200,"??????",url);
    }

    @RequestMapping(value = "uploadFile")
    @ResponseBody
    public String uploadFile(MultipartFile file, HttpServletRequest request) {
        String filePath = null;
        try {
            filePath = fdfsClientWrapper.uploadFile(file,request);
        } catch (IOException e) {
            log.error("?????????????????????{}", e);
            return "??????????????????";
        }
        return filePath;
    }

//    @GetMapping( "download")
//    public void downloadFile(@RequestParam("filePath") String filePath, HttpServletResponse response, HttpServletRequest request) {
//        ServletOutputStream outputStream = null;
//        try {
//            byte[] bytes = fdfsClientWrapper.downloadFile(filePath,request);
//            String fileName=filePath.substring(filePath.lastIndexOf("."));
//            System.out.println(fileName);
//            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("fdfs"+fileName, "UTF-8"));
//            response.setCharacterEncoding("UTF-8");
//            if (bytes != null) {
//                outputStream = response.getOutputStream();
//                outputStream.write(bytes);
//                outputStream.flush();
//            }
//        } catch (IOException e) {
//            log.debug("??????????????????????????????{}", e);
//        } finally {
//            try {
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException e) {
//                log.debug("??????????????????????????????{}", e);
//            }
//        }
//    }

    /**
     * ????????????
     *
     * @param filePath ????????????
     * @return ????????????
     */
    @GetMapping("delete")
    public String deleteFile(@RequestParam("filePath")String filePath) {
        fdfsClientWrapper.deleteFile(filePath);
        return "????????????";
    }

    /**
     * ???????????????
     *
     * @param
     * @return ????????????
     */
    @GetMapping("/deletes")
    public void deleteFiles(@RequestParam("files") List<String> files, @RequestParam("ids") List<Integer> ids, HttpServletRequest request) {
        fdfsClientWrapper.deleteFiles(files,ids,request);
    }


    @RequestMapping("/query")
    public List<Bucket> queryBucketList(){
        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();
        List<Bucket> buckets = null;
        try {
            buckets = cosClient.listBuckets();
            System.out.println(buckets);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();

        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
        return buckets;
    }

    /**
     * ????????????????????????????????????1????????????
     * @param
     */
    @PostMapping("/delete")
    public Result deleteFile(@RequestBody DeleteBucketKeyFrom from) {

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();
        try {
            cosClient.deleteObject(from.getBucketName(), from.getKey());
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }

        return Result.succ(200,"????????????",null);

    }


    public Result download(){


        return Result.succ(200,"??????",null);
    }






    @GetMapping("/getTwo")
    public Result getContent() {
        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();
        String bucket = "imgbucket-1307396532";
// ?????? bucket ?????????????????? delimiter???
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucket);
        // ?????? list ??? prefix, ?????? list ??????????????? key ??????????????? prefix ??????
        listObjectsRequest.setPrefix("shangpin/");
        // ?????? delimiter ???/, ??????????????????????????????????????????????????????????????????
//        listObjectsRequest.setDelimiter("/");
        // ?????? marker, (marker ???????????? list ?????????, ??????????????? list marker ??????)
        listObjectsRequest.setMarker("");
        // ???????????? list 100 ?????????,??????????????????, ????????? 1000 ???????????????????????? list 1000 ??? key???
        listObjectsRequest.setMaxKeys(100);
        ObjectListing objectListing = cosClient.listObjects(listObjectsRequest);
        // ???????????? list ??? marker
        String nextMarker = objectListing.getNextMarker();
        // ?????????????????? list ???, ?????? list ??????, ??? isTruncated ??? false, ????????? true
        boolean isTruncated = objectListing.isTruncated();
        List<COSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        for (COSObjectSummary cosObjectSummary : objectSummaries) {
            // get file path
            String key = cosObjectSummary.getKey();
            // get file length
            long fileSize = cosObjectSummary.getSize();
            // get file etag
            String eTag = cosObjectSummary.getETag();
            // get last modify time
            Date lastModified = cosObjectSummary.getLastModified();
            // get file save type
            String StorageClassStr = cosObjectSummary.getStorageClass();
        }
        return Result.succ(200,"??????",objectListing);
    }


    @GetMapping("/download")
    public Result getDownLoadFile(@RequestParam("key")String cosKey, HttpServletResponse response, HttpServletRequest request) throws IOException {

        String URL = "https://imgbucket-1307396532.cos.ap-guangzhou.myqcloud.com/";


        String suffix = cosKey.substring(cosKey.lastIndexOf(".") + 1);

        String fileName="aaa"+"."+suffix;

        String prefix = "image/";

        String bucket = "imgbucket-1307396532";

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        // ????????????????????????
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket,cosKey);//bucketName??????????????????
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();

        // ???????????????????????? http://blog.csdn.net/qq_35498405/article/details/77942817
        // ?????????????????????
        BufferedOutputStream outputStream=new BufferedOutputStream(response.getOutputStream());
        // ????????????????????????????????????
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));

        // ???????????? ??????????????????????????? ?????????????????????????????????
        BASE64Decoder base64Decoder = new BASE64Decoder();

        byte[] car=new byte[1024];
        int L=0;
        while((L=cosObjectInput.read(car))!=-1){

            if(car.length!=0){
                //L ????????????????????????????????????
                outputStream.write(car,0,L);
            }

        }
        if(outputStream!=null){
            outputStream.flush();
            outputStream.close();
        }
        return Result.succ(200,"??????",null);

         }

    @PostMapping("upload-file-tencent-cos4")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getUploadFile2TencentCosUrl4(@RequestParam("file") MultipartFile multipartFile,Principal principal){
        String URL = "https://imgbucket-1307396532.cos.ap-guangzhou.myqcloud.com/";

        String prefix="shangpin/";

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        String bucketName = "imgbucket-1307396532";

        String fileName = multipartFile.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            multipartFile.transferTo(localFile);
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            // ??? ??????????????? COS
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,localFile);
            cosClient.putObject(objectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }
        return Result.succ(200,"??????",URL+fileName);

    }


    @PostMapping("upload-file-tencent-cos6")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getUploadFile2TencentCosUrl6(@RequestParam("file") MultipartFile multipartFile,Principal principal){
        String URL = "https://imgbucket-1307396532.cos.ap-guangzhou.myqcloud.com/";

        String prefix="shoping_sliders/";

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        String bucketName = "imgbucket-1307396532";

        String fileName = multipartFile.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            multipartFile.transferTo(localFile);
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            // ??? ??????????????? COS
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,localFile);
            cosClient.putObject(objectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }

        shopingSlidersService.insert(URL+fileName);
        return Result.succ(200,"??????",URL+fileName);

    }


    @PostMapping("upload-file-tencent-cos7")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getUploadFile2TencentCosUrl7(@RequestParam("file") MultipartFile multipartFile,Principal principal){
        String URL = "https://imgbucket-1307396532.cos.ap-guangzhou.myqcloud.com/";

        String prefix="shoping_sliders/";

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        String bucketName = "imgbucket-1307396532";

        String fileName = multipartFile.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            multipartFile.transferTo(localFile);
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            // ??? ??????????????? COS
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,localFile);
            cosClient.putObject(objectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }

        shopingSlidersService.insertIcon(URL+fileName);
        return Result.succ(200,"??????",URL+fileName);

    }


    @PostMapping("upload-file-tencent-cos8")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getUploadFile2TencentCosUrl8(@RequestParam("file") MultipartFile multipartFile,Principal principal){
        String URL = "https://imgbucket-1307396532.cos.ap-guangzhou.myqcloud.com/";

        String prefix="shopping_merchants/";

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        String bucketName = "imgbucket-1307396532";

        String fileName = multipartFile.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            multipartFile.transferTo(localFile);
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            // ??? ??????????????? COS
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,localFile);
            cosClient.putObject(objectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }
        shopingSlidersService.insertShoppingRecruit(URL+fileName);
        return Result.succ(200,"??????",URL+fileName);
    }


    @PostMapping("upload-file-tencent-cos9")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getUploadFile2TencentCosUrl9(@RequestParam("file") MultipartFile multipartFile,Principal principal){
        String URL = "https://imgbucket-1307396532.cos.ap-guangzhou.myqcloud.com/";

        String prefix="shopping_category/";

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        String bucketName = "imgbucket-1307396532";

        String fileName = multipartFile.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            multipartFile.transferTo(localFile);
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            // ??? ??????????????? COS
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,localFile);
            cosClient.putObject(objectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }
        shopingSlidersService.insertShoppingCategory(URL+fileName);
        return Result.succ(200,"??????",URL+fileName);
    }

















}







