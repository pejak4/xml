import React from 'react';
import './Messages.css';
import HamburgerMenu from '../HamburgerMenu/HamburgerMenu';
import {updateObject} from '../utility';
import axios from '../axios-objects';
import {Redirect} from 'react-router-dom';
import NaviBar from './NaviBar/NaviBar';
import MessageInfo from './MessageInfo';
import MessageReply from './MessageReply';


class Messages extends React.PureComponent{

    constructor(){
        super();
        this.state = {
                userId: null, 
                message: [],
                from: '',
                meni: '',
                replyID: null,
                message2: []      
            };
    }
    


    componentDidMount = async() => {
        let userEmail = sessionStorage.getItem('userEmail'); 
        const data = {userEmail};
        const response = await axios.post('/authentication-service/getLoggedUser', data);
        if(response){
            this.setState({userId: response.data.id});
        }

       
       
        const response2 = await axios.get('/message-service/porukeza', {params:{id:this.state.userId}});
        if(response2){
            this.setState({message: response2.data});
            console.log(this.state.message);
        }

        const response3 = await axios.get('/message-service/porukeod', {params:{id:this.state.userId}});
        if(response3){
            this.setState({message2: response3.data});
            console.log(this.state.message2);
        }


    }

   
    processingMessage = async(idd, str) => {
        if(str === 'DELETE'){
            const response3 = await axios.get('/message-service/del', {params: {id: idd}});
            window.location.reload();
        }
    }

    replyMessage = (id) => {
        this.setState({meni: 'REPLY', replyID: id});
    }

    redirecting = async(text , status) => {
        if(status==='BACK'){
            this.setState({meni: 'INBOX'});
        }
        if(status==='REPLY'){
            const data = {senderId: this.state.userId, receiverId: this.state.replyID, message: text};
            const resp2 = await axios.post('/message-service/send',data);
            window.location.reload();
        }
    }

    renderMeni(){
        if(this.state.meni==='INBOX'){
            return this.state.message.map((m) =>{
                return (
                   <MessageInfo data={m} key={m.id} ret={this.processingMessage} ret2={this.replyMessage}/>
                );
            });
        }
        if(this.state.meni===''){
            return(<div><h3><b>Please choose what messages you want to see</b></h3></div>);
        }
        if(this.state.meni==='SENT'){
            return this.state.message2.map((m) =>{
                return (
                   <MessageInfo data={m} key={m.id} />
                );
            });
        }
        if(this.state.meni==='REPLY'){
            return(<MessageReply reply={this.state.replyID} actionn={this.redirecting}/>);
        }
        
    }

    send = (tip) => {
        this.setState({meni: tip});
    }


    render() {
        return (
        <div>
                <HamburgerMenu />
                <NaviBar ret={this.send}/>
                <header id="showcase">
                    <div className="container showcase-container" >
                       <div style={{ justifyContent: 'center', alignItems: 'center', overflow:'scroll'}}>
                            {this.renderMeni()}
                       </div>
                    </div>
                </header>
        </div>
        );
    }
}

export default Messages;