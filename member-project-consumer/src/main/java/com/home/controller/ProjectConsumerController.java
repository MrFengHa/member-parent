package com.home.controller;

import com.home.config.OSSProperties;
import com.home.constant.CrowdConstant;
import com.home.entity.vo.ProjectVO;
import com.home.util.CrowdUtil;
import com.home.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/4/7 9:41
 */
@Controller
public class ProjectConsumerController {
    @Autowired
    private OSSProperties ossProperties;

    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(
            //用于接收除了上传图片之外的普通数据
            ProjectVO projectVO,
            //接收我们上传的头图
            MultipartFile headerPicture,
            //接收上传的详情的图片
            List<MultipartFile> detailPictureList,
            //用来将收集一部分数据的ProjectVO对象存入Session域
            HttpSession httpSession,
            //用来操作失败后返回上一个表单页面携带提示消息
            ModelMap modelMap
    ) throws IOException {

        //1.头图的上传  获取当前对象是否为空
        boolean headerPictureEmpty = headerPicture.isEmpty();
        if (headerPictureEmpty) {
            //如果上传失败返回表单页面显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_HEADER_PIC_EMPTY);
            return "project-launch";
        }

        //如果headerPictureEmpty不为空，执行上传到OSS
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());

        String result = uploadHeaderPicResultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(result)) {
            //4.从返回的数据中获取图片路径
            String headerPicturePath = uploadHeaderPicResultEntity.getData();

            //5.存入到ProjectVO中
            projectVO.setHeaderPicturePath(headerPicturePath);
        } else {
            //如果上传失败返回表单页面显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        }

        //创建一个用来存放详情图片的集合
        List<String> detailPicturePathList = new ArrayList<String>();


        if (detailPictureList==null||detailPictureList.size()==0){
            //如果上传失败返回表单页面显示错误消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
            return "project-launch";
        }

        //4.遍历detailPictureList集合
        for (MultipartFile detailFile : detailPictureList) {
            if (detailFile.isEmpty()){
                //如果上传失败返回表单页面显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
            //5.判断detailPicture是否为空
            if (!detailFile.isEmpty()) {
                //6.执行上传
                ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(
                        ossProperties.getEndPoint(),
                        ossProperties.getAccessKeyId(),
                        ossProperties.getAccessKeySecret(),
                        detailFile.getInputStream(),
                        ossProperties.getBucketName(),
                        ossProperties.getBucketDomain(),
                        detailFile.getOriginalFilename()
                );
                //7.检查上传的结果
                String detailUploadResult = detailUploadResultEntity.getResult();
                if (ResultEntity.SUCCESS.equals(detailUploadResult)) {
                    String detailPicturePath = detailUploadResultEntity.getData();
                    //收集刚刚上传图片的访问路径
                    detailPicturePathList.add(detailPicturePath);
                }
            }
        }
        //9.将存放详情图片路径的集合存入到ProjectVO中
        projectVO.setDetailPicturePathList(detailPicturePathList);

        //10.将ProjectVO对象存入Session对象
        httpSession.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
        //11.以完整的访问路径前往下一个收集回报信息页面
        return "project-return";
    }
}
