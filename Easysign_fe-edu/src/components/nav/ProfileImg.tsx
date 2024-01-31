import basic_profile_img from "../../assets/images/basic_profile_img.svg";
import styled from "styled-components";

const Containers = styled.img`
  width: 140px;
  height: 140px;
  border-radius: 50%;
  cursor: pointer;
  &:hover {
    width: 132px;
    height: 132px;
    border: 5px solid #FFCFD8;
  }
`
  

interface ProfileImgProps {
  src?: string;
}


const ProfileImg = ({ src = basic_profile_img }: ProfileImgProps) => {
  return (
    <div>
      <Containers
        src={src}
        alt="profile_img"
        onClick={ () => ""}
      />
    </div>
  );
};

export default ProfileImg;
