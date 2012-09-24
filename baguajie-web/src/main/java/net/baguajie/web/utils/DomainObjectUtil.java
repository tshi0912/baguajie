package net.baguajie.web.utils;

import net.baguajie.constants.ApplicationConfig;
import net.baguajie.constants.Gender;
import net.baguajie.domains.Resource;
import net.baguajie.vo.ActivityVo;

import org.springframework.stereotype.Component;

@Component
public class DomainObjectUtil {
	
	public static String getGender(Gender gender){
		String sex = null;
		if(gender==null) return "保密";
		switch(gender){
			case FEMALE:
				sex = "美女";
				break;
			case MALE:
				sex = "帅哥";
				break;
			default:
				sex = "美女";
				break;
		}
		return sex;
	}
	
	public static String getThirdPerson(Gender gender){
		String sex = null;
		if(gender==null) return "他";
		switch(gender){
			case FEMALE:
				sex = "她";
				break;
			case MALE:
			default:
				sex = "他";
				break;
		}
		return sex;
	}
	
	public static String getAvatarUrl(Resource res,
			Gender gender){
		StringBuilder url = new StringBuilder();
		if(res!=null) {
			url.append(WebImageUtil.getImageUrl(res.getId()));
		}else{
			if(gender==null){
				url.append(ApplicationConfig.base)
					.append(ApplicationConfig.staticRefer)
					.append("/img/avatar-unkown.jpg");
			}else{
				switch(gender){
					case FEMALE:
						url.append(ApplicationConfig.base)
							.append(ApplicationConfig.staticRefer)
							.append("/img/avatar-female.jpg");
						break;
					case MALE:
						url.append(ApplicationConfig.base)
							.append(ApplicationConfig.staticRefer)
							.append("/img/avatar-male.jpg");
						break;
					default:
						url.append(ApplicationConfig.base)
							.append(ApplicationConfig.staticRefer)
							.append("/img/avatar-unkown.jpg");
						break;
				}
			}
		}
		return url.toString();
	}
	
	public static String getActivityHtml(ActivityVo activity){
		StringBuilder html = new StringBuilder();
		if(activity==null||activity.getType()==null) return html.toString();
		html.append("<a>").append(activity.getOwner().getName()).append("</a>");
		switch(activity.getType()){
			case SPOT:
				html.append("&nbsp;添加了新八卦&nbsp;")
					.append("<a>")
					.append(activity.getTargetSpot().getName());
				if(activity.getTargetSpot().getPlace()!=null){
					html.append("@")
						.append(activity.getTargetSpot().getPlace().getFullAddr());
				}
					html.append("</a>");
				break;
			case TRACK:
				html.append("&nbsp;追踪了&nbsp;")
					.append("<a>")
					.append(activity.getTargetSpot().getName());
				if(activity.getTargetSpot().getPlace()!=null){
					html.append("@")
						.append(activity.getTargetSpot().getPlace().getFullAddr());
				}
					html.append("</a>");
				break;
			case FORWARD:
				html.append("&nbsp;转发了&nbsp;")
					.append("<a>")
					.append(activity.getTargetSpot().getName());
				if(activity.getTargetSpot().getPlace()!=null){
					html.append("@")
						.append(activity.getTargetSpot().getPlace().getFullAddr());
				}
					html.append("</a>");
				break;
			case COMMENT:
				html.append("&nbsp;评论了&nbsp;");
				if(activity.getTargetSpot()!=null){
					html.append("<a>")
						.append(activity.getTargetSpot().getName());
					if(activity.getTargetSpot().getPlace()!=null){
						html.append("@")
							.append(activity.getTargetSpot().getPlace().getFullAddr());
					}
					html.append("</a>");
				}	
					html.append("&nbsp;\"")
						.append(activity.getContent())
						.append("\"");
				break;
			case FOLLOW:
				html.append("&nbsp;关注了&nbsp;")
					.append("<a>")
					.append(activity.getTargetUser().getName())
					.append("</a>");
				break;
			case SETTING:
				html.append("&nbsp;")
					.append(activity.getContent());
				break;
			default:
				break;
		}
		return html.toString();
	}
	
}
